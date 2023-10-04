package com.sevdotdev.seeminglyapokedex.dataimpl.repository

import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonLocalDataSource
import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonRemoteDataSource
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.fixtures.DomainTestFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultPokemonRepositoryTest {

    @MockK
    lateinit var localDataSource: PokemonLocalDataSource

    @MockK
    lateinit var remoteDataSource: PokemonRemoteDataSource

    @InjectMockKs
    lateinit var testSubject: DefaultPokemonRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `verify refresh pokemon data always calls localdatasource`() = runTest {
        coEvery { localDataSource.savePokemonList(any()) } just runs
        coEvery { remoteDataSource.getAllPokemon() } returns Result.failure(Exception("Oops"))
        testSubject.refreshPokemonData()
        coVerify { localDataSource.savePokemonList(emptyList()) }
    }

    @Test
    fun `assert if a data is present list flow returns success result`() = runTest {
        val flow = MutableStateFlow(listOf(DomainTestFixtures.createPokemonListItem()))
        every { localDataSource.getPokemonListFlow() } returns flow
        testSubject.getAllPokemonFlow().test {
            assert(awaitItem().isSuccess)
        }
    }

    @Test
    fun `assert if a data is missing list flow returns failure result`() = runTest {
        val flow = MutableStateFlow(emptyList<PokemonListItem>())
        every { localDataSource.getPokemonListFlow() } returns flow
        testSubject.getAllPokemonFlow().test {
            assert(awaitItem().isFailure)
        }
    }

    @Test
    fun `assert if pokemon present in local flow its emitted and no network call made`() = runTest {
        val expected = DomainTestFixtures.createSinglePokemon(name = "Tuffy")
        val flow = MutableStateFlow(expected)
        every { localDataSource.getSinglePokemonByNameFlow("Tuffy") } returns flow
        testSubject.getPokemonByNameFlow("Tuffy").test {
            assertEquals(Result.success(expected), awaitItem())
        }
        coVerify(exactly = 0) { remoteDataSource.getPokemonByName("Tuffy") }
    }

    @Test
    fun `assert if pokemon not present in local flow network call made and data saved`() = runTest {
        val expected = DomainTestFixtures.createSinglePokemon(name = "Tuffy")
        val flow: Flow<SinglePokemon?> = MutableStateFlow(null)
        every { localDataSource.getSinglePokemonByNameFlow("Tuffy") } returns flow
        coEvery { remoteDataSource.getPokemonByName("Tuffy") } returns Result.success(expected)
        coEvery { localDataSource.saveSinglePokemon(any()) } just runs
        testSubject.getPokemonByNameFlow("Tuffy").test {
            assertEquals(Result.success(expected), awaitItem())
        }
        coVerify(exactly = 1) { remoteDataSource.getPokemonByName("Tuffy") }
        coVerify(exactly = 1) { localDataSource.saveSinglePokemon(expected) }
    }

    @Test
    fun `assert network failure returns failed result and does not call save`() = runTest {
        val expectedEx = Exception("Missing Data")
        val flow: Flow<SinglePokemon?> = MutableStateFlow(null)
        every { localDataSource.getSinglePokemonByNameFlow("Tuffy") } returns flow
        coEvery { remoteDataSource.getPokemonByName("Tuffy") } returns Result.failure(expectedEx)
        testSubject.getPokemonByNameFlow("Tuffy").test {
            assertEquals(Result.failure<SinglePokemon>(expectedEx), awaitItem())
        }
        coVerify(exactly = 1) { remoteDataSource.getPokemonByName("Tuffy") }
        coVerify(exactly = 0) { localDataSource.saveSinglePokemon(any()) }
    }
}