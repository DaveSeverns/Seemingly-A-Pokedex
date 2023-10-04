package com.sevdotdev.seeminglyapokedex.domain.usecase

import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.SomethingWentWrong
import com.sevdotdev.seeminglyapokedex.fixtures.DomainTestFixtures
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPokemonDetailsUseCaseTest {

    @MockK
    lateinit var pokemonRepository: PokemonRepository

    @InjectMockKs
    lateinit var testSubject: GetPokemonDetailsUseCase

    private val pokemonName = "Tuffy"

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `assert non null success returns success data result`() = runTest {
        val expected = DomainTestFixtures.createSinglePokemon(name = pokemonName)
        every { pokemonRepository.getPokemonByNameFlow(pokemonName) } returns flowOf(Result.success(expected))
        testSubject(pokemonName).test {
            assertEquals(PokeDataResult.Success(expected), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `assert null success returns invalid data result`() = runTest {
        every { pokemonRepository.getPokemonByNameFlow(pokemonName) } returns flowOf(Result.success(null))
        testSubject(pokemonName).test {
            assertEquals(PokeDataResult.Failure(InvalidData), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `assert failure returns something went wrong data result`() = runTest {
        val exception = Exception()
        every { pokemonRepository.getPokemonByNameFlow(pokemonName) } returns flowOf(Result.failure(exception))
        testSubject(pokemonName).test {
            assertEquals(PokeDataResult.Failure(SomethingWentWrong(null)), awaitItem())
            awaitComplete()
        }
    }
}