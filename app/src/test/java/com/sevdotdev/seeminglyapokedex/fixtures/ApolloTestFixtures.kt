package com.sevdotdev.seeminglyapokedex.fixtures

import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery
import com.sevdotdev.seeminglyapokedex.PokemonListQuery

/**
 * Reusable fixtures to be used through relevant tests which rely on interactions with the Apollo Client
 */
object ApolloTestFixtures {
    fun createListQueryResponse(
        results: List<PokemonListQuery.Result> = listOf(createPokemonsListResult())
    ): PokemonListQuery.Data = PokemonListQuery.Data(
        pokemons = PokemonListQuery.Pokemons(
            results = results,
            prevOffset = 0,
            nextOffset = 0,
            __typename = "pokemons"
        )
    )

    fun createPokemonsListResult(
        name: String = Defaults.DEFAULT_NAME,
        imageUrl: String = "url",
        id: Int = 1
    ): PokemonListQuery.Result = PokemonListQuery.Result(
        name = name,
        image = imageUrl,
        id = id
    )

    fun createPokemonByNameQueryResponse(
        name: String = Defaults.DEFAULT_NAME,
        types: List<String> = listOf("grass")
    ): PokemonByNameQuery.Data = PokemonByNameQuery.Data(
        pokemon = PokemonByNameQuery.Pokemon(
            name = name,
            types = types.mapIndexed {index, type ->
                PokemonByNameQuery.Type(
                    index,
                    PokemonByNameQuery.Type1(name = type, url = null)
                )
            },
            height = 0,
            weight = 0,
            moves = null,
            stats = null,
            sprites = null
        )
    )

    object Defaults {
        const val DEFAULT_NAME = "bulbasaur"
    }
}