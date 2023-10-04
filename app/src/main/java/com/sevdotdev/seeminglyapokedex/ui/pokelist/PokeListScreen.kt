package com.sevdotdev.seeminglyapokedex.ui.pokelist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.domain.model.PokeDataResult
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.fold
import com.sevdotdev.seeminglyapokedex.ui.common.DefaultErrorContent
import com.sevdotdev.seeminglyapokedex.ui.common.DefaultLoadingContent
import com.sevdotdev.seeminglyapokedex.ui.navigation.NavRoute
import com.sevdotdev.seeminglyapokedex.ui.navigation.Navigator
import com.sevdotdev.seeminglyapokedex.ui.pokelist.PokeListAction.RefreshClicked
import com.sevdotdev.seeminglyapokedex.ui.theme.SeeminglyAPokeDexTheme

/**
 * Overall Screen for displaying a high level list of all available pokemon. The screen can exist
 * at the top level of the navigation graph and relies of a ViewModel to hold it's state.
 */
@Composable
fun PokeListScreen(
    navigator: Navigator,
    viewModel: PokeListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val onPokemonClicked: (String) -> Unit = { name ->
        navigator.navigateTo(NavRoute.PokemonDetails, listOf(name))
    }

    PokeListContent(
        state = state,
        onPokemonClicked = onPokemonClicked,
        onRetryActionClicked = {
            viewModel.submitAction(RefreshClicked)
        },
    )


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokeListContent(
    state: PokeDataResult<List<PokemonListItem>>,
    onPokemonClicked: (name: String) -> Unit,
    onRetryActionClicked: () -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Column(Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = stringResource(id = R.string.pokemon_list_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .shadow(elevation = 4.dp)
                )
            }
        }
    ) {
        state.fold(
            isFailure = { error ->
                DefaultErrorContent(
                    error = error,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(
                            horizontal = 16.dp
                        ),
                    onRetryAction = onRetryActionClicked,
                )
            },
            isLoading = {
                DefaultLoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
            },
            isSuccess = { pokemons ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(it),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {
                    items(pokemons.size) { index ->
                        PokeListItemCard(
                            pokemon = pokemons[index],
                            onPokemonClicked = onPokemonClicked
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewPokeListContent() {
    SeeminglyAPokeDexTheme {
        PokeListContent(
            state = PokeDataResult.Success(
                listOf(
                    PokemonListItem("Dave", "url", "1"),
                    PokemonListItem("Dave2", "url", "2")
                )
            ),
            onPokemonClicked = {},
            onRetryActionClicked = {},
        )
    }
}