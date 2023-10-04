package com.sevdotdev.seeminglyapokedex.domain.usecase

import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.SomethingWentWrong
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
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

class GetListOfPokemonUseCaseTest {

    @MockK
    lateinit var pokemonRepository: PokemonRepository

    @InjectMockKs
    lateinit var testSubject: GetListOfPokemonUseCase

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `assert success from repo with valid data is mapped to data result success`() = runTest {
        val expectedList = listOf<PokemonListItem>(DomainTestFixtures.createPokemonListItem())
        every { pokemonRepository.getAllPokemonFlow() } returns flowOf(Result.success(expectedList))
        testSubject().test {
            assertEquals(PokeDataResult.Success(expectedList), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `assert empty list from repo is mapped to invalid data result`() = runTest {
        val emptyList: List<PokemonListItem> = emptyList()
        every { pokemonRepository.getAllPokemonFlow() } returns flowOf(Result.success(emptyList))
        testSubject().test {
            assertEquals(PokeDataResult.Failure(InvalidData), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `assert failure returns something went wrong error with message`() = runTest {
        val message = "Oopsie daisy"
        val exception = Exception(message)
        every { pokemonRepository.getAllPokemonFlow() } returns flowOf(Result.failure(exception))
        testSubject().test {
            assertEquals(PokeDataResult.Failure(SomethingWentWrong(message)), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `assert list data is filtered correctly`() = runTest {
        val validListItem = DomainTestFixtures.createPokemonListItem()
        val missingNameListItem = validListItem.copy(name = "")
        val missingIdListItem = validListItem.copy(id = "")
        val listFromRepo = listOf(validListItem, missingIdListItem, missingNameListItem)
        every { pokemonRepository.getAllPokemonFlow() } returns flowOf(Result.success(listFromRepo))
        testSubject().test {
            assert((awaitItem() as? PokeDataResult.Success)?.data?.size == 1)
            awaitComplete()
        }
    }
}
