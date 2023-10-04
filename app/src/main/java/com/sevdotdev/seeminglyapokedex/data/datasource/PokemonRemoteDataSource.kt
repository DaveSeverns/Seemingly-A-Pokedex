package com.sevdotdev.seeminglyapokedex.data.datasource

import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon

/**
 * Abstraction for a remote source of data for
 * retrieving list of [PokemonListItem] as well as retrieving details for a [SinglePokemon].
 * Use when need to interface with specific client and API implementations
 */
interface PokemonRemoteDataSource {
    /**
     * Makes a request to the network data source and returns a [Result] encapsulating a
     * list of [PokemonListItem] if successful or an offending exception if a the request fails.
     */
    suspend fun getAllPokemon(): Result<List<PokemonListItem>>

    /**
     * Makes a request to the network data source and returns a [Result] encapsulating a
     * ø[SinglePokemon] if successful or an offending exception if a the request fails.
     */
    suspend fun getPokemonByName(name: String): Result<SinglePokemon?>
}