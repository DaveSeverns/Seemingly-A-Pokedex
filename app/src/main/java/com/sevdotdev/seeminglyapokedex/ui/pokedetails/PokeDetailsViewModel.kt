package com.sevdotdev.seeminglyapokedex.ui.pokedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.domain.usecase.GetPokemonDetailsUseCase
import com.sevdotdev.seeminglyapokedex.ui.navigation.NavRoute
import com.sevdotdev.seeminglyapokedex.ui.pokedetails.PokeDetailsAction.RetryFetchPokemon
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
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {

    private val pokemonName: String = savedStateHandle[NavRoute.PokemonDetails.ARG_NAME] ?: error(
        "Now is not the time to ask: \"Who's that Pokémon?\""
    )
    private val _state: MutableStateFlow<PokeDataResult<SinglePokemon>> =
        MutableStateFlow(PokeDataResult.Loading)
    val state: StateFlow<PokeDataResult<SinglePokemon>> = _state.asStateFlow()

    init {
        fetchSinglePokemon()
    }

    /**
     * Public api to allow for submission of [PokeDetailsAction] to the
     * ViewModel.
     */
    fun submitAction(action: PokeDetailsAction) {
        when (action) {
            RetryFetchPokemon -> {
                _state.tryEmit(PokeDataResult.Loading)
                fetchSinglePokemon()
            }
        }
    }

    private fun fetchSinglePokemon() = getPokemonDetailsUseCase(pokemonName).onEach {
        _state.emit(it)
    }.launchIn(viewModelScope)

}

/**
 * Sealed class representing various user interactions from the
 * [com.sevdotdev.seeminglyapokedex.ui.pokedetails.PokeDetailsScreen] that the [PokeDetailsViewModel]
 * wants to respond to.
 */
sealed interface PokeDetailsAction {
    object RetryFetchPokemon : PokeDetailsAction
}