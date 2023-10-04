package com.sevdotdev.seeminglyapokedex.domain.usecase

import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.InvalidData
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError.SomethingWentWrong
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Usecase interactor which requests a [SinglePokemon] from the [pokemonRepository] and
 * applies business rules to determine if the data is valid.
 */
class GetPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    /**
     * Invoke the use case, if a `null` payload is returned in a successful result it
     * should be mapped as invalid.
     */
    operator fun invoke(name: String): Flow<PokeDataResult<SinglePokemon>> =
        pokemonRepository.getPokemonByNameFlow(name).map {
            it.fold(
                onFailure = { err ->
                    PokeDataResult.Failure(SomethingWentWrong(err.message))
                },
                onSuccess = { pokemon ->
                    pokemon?.let {
                        PokeDataResult.Success(pokemon)
                    } ?: PokeDataResult.Failure(InvalidData)
                }
            )
        }
}