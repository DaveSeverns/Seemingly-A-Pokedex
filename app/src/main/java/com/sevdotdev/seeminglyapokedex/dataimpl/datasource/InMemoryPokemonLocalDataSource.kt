package com.sevdotdev.seeminglyapokedex.dataimpl.datasource

import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonLocalDataSource
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * An in memory implementation of a [PokemonLocalDataSource]. While determining more potential
 * features and choosing an appropriate method for persistence this class will allow
 * development of other areas to continue and for the application to function as expected
 * on a per session basis.
 *
 * This is explicitly marked as a Singlton as we will want to require requested dependency on
 * this class to utilize the same instance as the the cached data will only live in memory.
 */
@Singleton
class InMemoryPokemonLocalDataSource @Inject constructor() : PokemonLocalDataSource {

    /**
     * Using a SharedFlow here as we want to avoid emiting an intialized value when subscribed to
     * as well as allow repeat values to be emitted as source of this flow can be refreshed.
     */
    private val pokemonListItemFlow: MutableSharedFlow<List<PokemonListItem>> =
        MutableSharedFlow(replay = 0)
    private val singlePokemonCache: MutableStateFlow<List<SinglePokemon>> = MutableStateFlow(
        emptyList()
    )

    override fun getPokemonListFlow(): Flow<List<PokemonListItem>> =
        pokemonListItemFlow.combine(
            singlePokemonCache,
        ) { pokeList: List<PokemonListItem>, listOfSingleMons: List<SinglePokemon> ->
            pokeList.map { item ->
                val cachedPokemon: SinglePokemon? =
                    listOfSingleMons.find { singlePokemon -> singlePokemon.name == item.name }
                /**
                 * The API does not provide this value initially but it is a pretty crucial piece
                 * of information when viewing pokemon at a glance,
                 * taking advantage of the combine operator to
                 * observe any updatesto the [SinglePokemon] details flow if the type becomes
                 * avaiable.
                 */
                if (item.type == PokemonType.UNKNOWN && cachedPokemon != null) {
                    item.copy(type = cachedPokemon.types[0])
                } else {
                    item
                }
            }
        }

    override fun getSinglePokemonByNameFlow(name: String): Flow<SinglePokemon?> =
        singlePokemonCache.map { current ->
            current.firstOrNull { singlePokemon ->
                singlePokemon.name == name
            }
        }


    override suspend fun savePokemonList(pokemons: List<PokemonListItem>) {
        pokemonListItemFlow.emit(pokemons)
    }

    override suspend fun saveSinglePokemon(pokemon: SinglePokemon) {
        val current = singlePokemonCache.value.toMutableList()
        current.add(pokemon)
        singlePokemonCache.emit(current)
    }
}