package com.sevdotdev.seeminglyapokedex.ui.pokedetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.usecase.GetPokemonDetailsUseCase
import com.sevdotdev.seeminglyapokedex.fixtures.DomainTestFixtures
import com.sevdotdev.seeminglyapokedex.rules.TestDispatcherRule
import com.sevdotdev.seeminglyapokedex.ui.navigation.NavRoute
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherRule::class)
class PokeDetailsViewModelTest{

    @MockK
    lateinit var useCase: GetPokemonDetailsUseCase

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `assert initial state is loading`() = runTest {
        every { useCase(any()) } returns flowOf(PokeDataResult.Failure(InvalidData))
        val testSubject = PokeDetailsViewModel(
            savedStateHandle = SavedStateHandle().also {
                it[NavRoute.PokemonDetails.ARG_NAME] = DomainTestFixtures.Defaults.DEFAULT_NAME
            },
            getPokemonDetailsUseCase = useCase
        )

        testSubject.state.test {
            assertEquals(PokeDataResult.Loaidng, awaitItem())
        }
    }

    @Test
    fun `state updates to show error`() = runTest {
        every { useCase(any()) } returns flowOf(PokeDataResult.Failure(InvalidData))
        val testSubject = PokeDetailsViewModel(
            savedStateHandle = SavedStateHandle().also {
                it[NavRoute.PokemonDetails.ARG_NAME] = DomainTestFixtures.Defaults.DEFAULT_NAME
            },
            getPokemonDetailsUseCase = useCase
        )

        testSubject.state.test {
            awaitItem() // Initial value
            assertEquals(PokeDataResult.Failure(InvalidData), awaitItem())
        }
    }

    @Test
    fun `state updates to show success`() = runTest {
        val pokemon = DomainTestFixtures.createSinglePokemon(name = "Tuffy")
        every { useCase(any()) } returns flowOf(PokeDataResult.Success(pokemon))
        val testSubject = PokeDetailsViewModel(
            savedStateHandle = SavedStateHandle().also {
                it[NavRoute.PokemonDetails.ARG_NAME] = DomainTestFixtures.Defaults.DEFAULT_NAME
            },
            getPokemonDetailsUseCase = useCase
        )

        testSubject.state.test {
            awaitItem() // Initial value
            assertEquals(PokeDataResult.Success(pokemon), awaitItem())
        }
    }

    @Test
    fun `assert illegal state thrown in name argument is missing in saved state`() = runTest {
        try {
            PokeDetailsViewModel(savedStateHandle = SavedStateHandle(),
                getPokemonDetailsUseCase = useCase
            )
        } catch (e: Exception) {
            assert(e is IllegalStateException)
        }
    }
}