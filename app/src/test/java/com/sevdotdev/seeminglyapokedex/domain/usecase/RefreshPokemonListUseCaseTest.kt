package com.sevdotdev.seeminglyapokedex.domain.usecase

import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RefreshPokemonListUseCaseTest {

    @MockK
    lateinit var pokemonRepository: PokemonRepository

    @InjectMockKs
    lateinit var testSubject: RefreshPokemonListUseCase

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `verify repository is called when invoked`() = runTest {
        coEvery { pokemonRepository.refreshPokemonData() } just runs
        testSubject()
        coVerify { pokemonRepository.refreshPokemonData() }
    }
}