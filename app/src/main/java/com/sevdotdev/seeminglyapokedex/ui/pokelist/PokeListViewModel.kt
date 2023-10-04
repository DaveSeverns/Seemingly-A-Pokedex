package com.sevdotdev.seeminglyapokedex.ui.pokelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.usecase.GetListOfPokemonUseCase
import com.sevdotdev.seeminglyapokedex.domain.usecase.RefreshPokemonListUseCase
import com.sevdotdev.seeminglyapokedex.ui.pokelist.PokeListAction.RefreshClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for holding the state for the
 * [com.sevdotdev.seeminglyapokedex.ui.pokelist.PokeListScreen]
 */
@HiltViewModel
class PokeListViewModel @Inject constructor(
    getListOfPokemonUseCase: GetListOfPokemonUseCase,
    private val refreshPokemonListUseCase: RefreshPokemonListUseCase,
): ViewModel() {

    private var refreshJob: Job? = null
    private val _state: MutableStateFlow<PokeDataResult<List<PokemonListItem>>> = MutableStateFlow(PokeDataResult.Loaidng)
    val state: StateFlow<PokeDataResult<List<PokemonListItem>>> = _state.asStateFlow()

    init {
        getListOfPokemonUseCase().onEach {
            _state.emit(it)
        }.launchIn(viewModelScope)

        refreshPokemon()
    }

    /**
     * Public api to allow for submission of [PokeListAction] to the
     * ViewModel.
     */
    fun submitAction(action: PokeListAction) {
        when (action) {
            RefreshClicked -> {
                handleRefreshClicked()
            }
        }
    }

    private fun handleRefreshClicked() {
        _state.tryEmit(PokeDataResult.Loaidng)
        refreshPokemon()
    }

    private fun refreshPokemon() {
        if (refreshJob?.isActive == true){
            return
        }
        refreshJob = viewModelScope.launch {
            refreshPokemonListUseCase()
        }
    }

}

/**
 * Sealed class representing various user interactions from the
 * [com.sevdotdev.seeminglyapokedex.ui.pokelist.PokeListScreen] that the [PokeListViewModel]
 * wants to respond to.
 */
sealed interface PokeListAction {
    object RefreshClicked: PokeListAction
}