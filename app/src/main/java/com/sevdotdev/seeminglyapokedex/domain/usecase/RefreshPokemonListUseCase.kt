package com.sevdotdev.seeminglyapokedex.domain.usecase

import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import javax.inject.Inject

/**
 * Usecase interactor to request a refresh of the pokemon list data from
 * the [PokemonRepository]
 */
class RefreshPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke() {
        pokemonRepository.refreshPokemonData()
    }
}