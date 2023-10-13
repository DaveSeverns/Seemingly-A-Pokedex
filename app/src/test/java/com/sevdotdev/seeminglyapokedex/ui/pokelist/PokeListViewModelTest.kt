package com.sevdotdev.seeminglyapokedex.ui.pokelist

import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.usecase.GetListOfPokemonUseCase
import com.sevdotdev.seeminglyapokedex.domain.usecase.RefreshPokemonListUseCase
import com.sevdotdev.seeminglyapokedex.rules.TestDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherRule::class)
class PokeListViewModelTest {

    @MockK
    lateinit var refreshPokemonListUseCase: RefreshPokemonListUseCase

    @MockK
    lateinit var getListOfPokemonUseCase: GetListOfPokemonUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `assert intial state of loading`() = runTest {
        every { getListOfPokemonUseCase.invoke() } returns emptyFlow()
        coEvery { refreshPokemonListUseCase() } just runs
        val testSubject = PokeListViewModel(
            refreshPokemonListUseCase = refreshPokemonListUseCase,
            getListOfPokemonUseCase = getListOfPokemonUseCase,
        )
        testSubject.state.test {
            assertEquals(PokeDataResult.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates to show error`() = runTest {
        every { getListOfPokemonUseCase.invoke() } returns flowOf(PokeDataResult.Failure(InvalidData))
        coEvery { refreshPokemonListUseCase() } just runs
        val testSubject = PokeListViewModel(
            refreshPokemonListUseCase = refreshPokemonListUseCase,
            getListOfPokemonUseCase = getListOfPokemonUseCase,
        )
        testSubject.state.test {
            awaitItem() // initial
            assertEquals(PokeDataResult.Failure(InvalidData), awaitItem())
        }
    }

    @Test
    fun `state updates to show success`() = runTest {
        every { getListOfPokemonUseCase.invoke() } returns flowOf(PokeDataResult.Success(emptyList()))
        coEvery { refreshPokemonListUseCase() } just runs
        val testSubject = PokeListViewModel(
            refreshPokemonListUseCase = refreshPokemonListUseCase,
            getListOfPokemonUseCase = getListOfPokemonUseCase,
        )
        testSubject.state.test {
            awaitItem() // initial
            assertEquals(PokeDataResult.Success<List<PokemonListItem>>(emptyList()), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify refresh action updates state and launchs use case`() = runTest {
        every { getListOfPokemonUseCase.invoke() } returns flowOf(PokeDataResult.Success(emptyList()))
        coEvery { refreshPokemonListUseCase() } just runs
        val testSubject = PokeListViewModel(
            refreshPokemonListUseCase = refreshPokemonListUseCase,
            getListOfPokemonUseCase = getListOfPokemonUseCase,
        )
        testSubject.state.test {
            awaitItem() // initial
            awaitItem() // from use case
            testSubject.submitAction(PokeListAction.RefreshClicked)

            assertEquals(PokeDataResult.Loading, awaitItem())
        }
        advanceUntilIdle()
        coVerify(exactly = 1) { refreshPokemonListUseCase() }
    }
}