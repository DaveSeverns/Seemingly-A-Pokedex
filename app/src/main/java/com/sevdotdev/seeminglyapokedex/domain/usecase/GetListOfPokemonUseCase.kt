package com.sevdotdev.seeminglyapokedex.domain.usecase

import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.SomethingWentWrong
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Usecase interactor which requests a list of [PokemonListItem] from the [pokemonRepository] and
 * applies business rules to determine if the data is valid.
 */
class GetListOfPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    /**
     * Operator function to invoke the usecase. If a item in the list is
     * missing either the name or the id it will be filtered out of the returned list
     */
    operator fun invoke(): Flow<PokeDataResult<List<PokemonListItem>>> =
        pokemonRepository.getAllPokemonFlow().map { result ->
            result.fold(
                onFailure = {
                    PokeDataResult.Failure(SomethingWentWrong(it.message))
                },
                onSuccess = { pokemons ->
                    val filteredList = pokemons.filter { item ->
                        item.name.isNotEmpty() && item.id.isNotEmpty()
                    }
                    if (filteredList.isEmpty()) {
                        PokeDataResult.Failure(InvalidData)
                    } else {
                        PokeDataResult.Success(filteredList)
                    }
                }
            )
        }
}