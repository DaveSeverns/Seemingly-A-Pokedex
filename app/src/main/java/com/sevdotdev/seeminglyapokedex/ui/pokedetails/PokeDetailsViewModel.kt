package com.sevdotdev.seeminglyapokedex.ui.pokedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.domain.usecase.GetPokemonDetailsUseCase
import com.sevdotdev.seeminglyapokedex.ui.navigation.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel responsible for holding the state for the
 * [com.sevdotdev.seeminglyapokedex.ui.pokedetails.PokeDetailsScreen]
 */
@HiltViewModel
class PokeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonDetailsUseCase: GetPokemonDetailsUseCase
): ViewModel() {

    private val pokemonName: String = savedStateHandle[NavRoute.PokemonDetails.ARG_NAME] ?: error(
        "Now is not the time to ask: \"Who's that Pokémon?\""
    )
    private val _state: MutableStateFlow<PokeDataResult<SinglePokemon>> = MutableStateFlow(PokeDataResult.Loaidng)
    val state: StateFlow<PokeDataResult<SinglePokemon>> = _state.asStateFlow()

    init {
        getPokemonDetailsUseCase(pokemonName).onEach {
            _state.emit(it)
        }.launchIn(viewModelScope)
    }

}