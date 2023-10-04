package com.sevdotdev.seeminglyapokedex.data.datasource

import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for a locally owned, in app or on device, source of data for
 * retrieving and saving Lists of [PokemonListItem] as well as retrieving and
 * saving a [SinglePokemon]
 */
interface PokemonLocalDataSource {
    /**
     * Gets an observable flow of all available [PokemonListItem] in a list.
     * A non-nullable list is always returned
     *
     * @return [Flow][List][PokemonListItem]
     */
    fun getPokemonListFlow(): Flow<List<PokemonListItem>>

    /**
     * Gets an observable flow of the requested [SinglePokemon] by the [name].
     * A `null` value is possible if the object is not found in the data source.
     *
     * @param name of type [String], the name of the pokemon to return
     * @return [Flow][SinglePokemon]
     */
    fun getSinglePokemonByNameFlow(name: String): Flow<SinglePokemon?>

    /**
     * Save the passed in list to the data source
     *
     * @param pokemons [List][PokemonListItem] list to be saved.
     *
     */
    suspend fun savePokemonList(pokemons: List<PokemonListItem>)

    /**
     * Save the passed in object to the data source
     *
     * @param pokemon [SinglePokemon] to be saved.
     *
     */
    suspend fun saveSinglePokemon(pokemon: SinglePokemon)
}