package com.sevdotdev.seeminglyapokedex.data.repository

import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow

/**
 * PokemonRepository's goal is to be a interface from the data to the domain or ui layer of the app.
 * A dependent class should use the repository to interact with the datasources. The repository's main role
 * is to therefore respond to these request and appropriately mediate the necessary actions and
 * interactions between local and remote data sources.
 */
interface PokemonRepository {
    /**
     * When called the result should be a refresh or sync of any network reliant pokemon data
     */
    suspend fun refreshPokemonData()

    /**
     * Returns a flow of a [Result] which wraps a list of [PokemonListItem]
     */
    fun getAllPokemonFlow(): Flow<Result<List<PokemonListItem>>>

    /**
     * Returns a flow of a [Result] which wraps [SinglePokemon]
     */
    fun getPokemonByNameFlow(name: String): Flow<Result<SinglePokemon?>>
}