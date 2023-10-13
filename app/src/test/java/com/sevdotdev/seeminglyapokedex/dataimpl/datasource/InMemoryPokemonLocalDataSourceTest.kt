package com.sevdotdev.seeminglyapokedex.dataimpl.datasource

import app.cash.turbine.test
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.fixtures.DomainTestFixtures
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class InMemoryPokemonLocalDataSourceTest {

    private val testSubject = InMemoryPokemonLocalDataSource()

    @Test
    fun `pokemon list flow will emit initial state empty list`() = runTest {
        testSubject.getPokemonListFlow().test {
            assertEquals(emptyList<PokemonListItem>(), awaitItem())
        }
    }

    @Test
    fun `single pokemon by name flow will emit with initial empty value`() = runTest {
        testSubject.getSinglePokemonByNameFlow("Any").test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `pokemon list flow will emit after event is saved`() = runTest {
        testSubject.getPokemonListFlow().test {
            testSubject.savePokemonList(emptyList())
            assertEquals(emptyList<PokemonListItem>(), awaitItem())
        }
    }

    @Test
    fun `single pokemon flow emits new value when item is saved`() = runTest {
        val expected = DomainTestFixtures.createSinglePokemon("Tuffy")
        testSubject.getSinglePokemonByNameFlow("Tuffy").test {
            assertNull(awaitItem()) // initial value
            testSubject.saveSinglePokemon(DomainTestFixtures.createSinglePokemon("Tuffy"))
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `pokemon list flow updates if details for a item in list details are cached`() = runTest {
        val pokemonListItem =
            DomainTestFixtures.createPokemonListItem(DomainTestFixtures.Defaults.DEFAULT_NAME)
        val singlePokemon =
            DomainTestFixtures.createSinglePokemon(DomainTestFixtures.Defaults.DEFAULT_NAME)
        testSubject.getPokemonListFlow().test {
            awaitItem() // empty value
            testSubject.savePokemonList(listOf(pokemonListItem))
            assertEquals(PokemonType.UNKNOWN, awaitItem()[0].type)
            testSubject.saveSinglePokemon(singlePokemon)
            assertEquals(PokemonType.FIRE, awaitItem()[0].type)
        }
    }
}