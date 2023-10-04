package com.sevdotdev.seeminglyapokedex.dataimpl.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery
import com.sevdotdev.seeminglyapokedex.PokemonListQuery
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.fixtures.ApolloTestFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ApolloPokemonRemoteDataSourceTest {

    @MockK
    lateinit var mockClient: ApolloClient

    @InjectMockKs
    lateinit var testSubject: ApolloPokemonRemoteDataSource

    @BeforeEach
    fun setup() =
        MockKAnnotations.init(this)


    @Test
    fun `returns a Success Result when list query request completes`() = runTest {
        val mockResponse = mockk<ApolloResponse<PokemonListQuery.Data>>()
        every { mockResponse.dataAssertNoErrors } returns ApolloTestFixtures.createListQueryResponse(
            listOf(ApolloTestFixtures.createPokemonsListResult())
        )
        coEvery {
            mockClient.query(PokemonListQuery(Optional.present(0), Optional.present(151))).execute()
        } returns
                mockResponse
        val result = testSubject.getAllPokemon()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `returns a Failure Result if client throws exception on list query`() = runTest {
        coEvery {
            mockClient.query(PokemonListQuery(Optional.present(0), Optional.present(151))).execute()
        } throws
                Exception("Uh oh!")
        val result = testSubject.getAllPokemon()
        assertTrue(result.isFailure)
    }

    @Test
    fun `returns a Success Result when get pokemon by name query completes`() = runTest {
        val mockResponse = mockk<ApolloResponse<PokemonByNameQuery.Data>>()
        val pokemonName = "Tuffy" // my dog
        every { mockResponse.dataAssertNoErrors } returns ApolloTestFixtures.createPokemonByNameQueryResponse()
        coEvery { mockClient.query(PokemonByNameQuery(pokemonName)).execute() } returns mockResponse
        val result = testSubject.getPokemonByName(pokemonName)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `returns a Failure Result if client throws exception on name query`() = runTest {
        val pokemonName = "Tuffy" // my dog
        coEvery { mockClient.query(PokemonByNameQuery(pokemonName)).execute() } throws
                Exception("Uh oh!")
        val result = testSubject.getPokemonByName(pokemonName)
        assertTrue(result.isFailure)
    }

    @Test
    fun `assert correct domain object is returned for list query`() = runTest {
        val expectedName = "Tuffy"
        val mockResponse = mockk<ApolloResponse<PokemonListQuery.Data>>()
        every { mockResponse.dataAssertNoErrors } returns ApolloTestFixtures.createListQueryResponse(
            listOf(ApolloTestFixtures.createPokemonsListResult(name = expectedName))
        )
        coEvery {
            mockClient.query(PokemonListQuery(Optional.present(0), Optional.present(151))).execute()
        } returns
                mockResponse

        val result = testSubject.getAllPokemon()

        assertEquals(expectedName, result.getOrNull()?.get(0)?.name)
    }

    @Test
    fun `assert correct domain object is returned for single pokemon query`() = runTest {
        val expectedName = "Tuffy"
        val mockResponse = mockk<ApolloResponse<PokemonByNameQuery.Data>>()
        every { mockResponse.dataAssertNoErrors } returns ApolloTestFixtures.createPokemonByNameQueryResponse(
            name = expectedName,
            types = listOf("fire", "ice")
        )
        coEvery {
            mockClient.query(PokemonByNameQuery(expectedName)).execute()
        } returns
                mockResponse

        val result = testSubject.getPokemonByName(expectedName).getOrThrow()

        assertEquals(expectedName, result?.name)
        assertTrue(result?.types?.contains(PokemonType.ICE) == true)
        assertTrue(result?.types?.contains(PokemonType.FIRE) == true)
        assertFalse(result?.types?.contains(PokemonType.GRASS) == true)
    }
}