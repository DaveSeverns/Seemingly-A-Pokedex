package com.sevdotdev.seeminglyapokedex.ui.pokedetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.domain.model.fold
import com.sevdotdev.seeminglyapokedex.ui.common.DefaultErrorContent
import com.sevdotdev.seeminglyapokedex.ui.common.DefaultLoadingContent
import com.sevdotdev.seeminglyapokedex.ui.common.TypePill
import com.sevdotdev.seeminglyapokedex.ui.pokedetails.PokeDetailsAction.RetryFetchPokemon
import com.sevdotdev.seeminglyapokedex.ui.pokedetails.components.BaseStatsCard
import com.sevdotdev.seeminglyapokedex.ui.pokedetails.components.SpriteCard
import com.sevdotdev.seeminglyapokedex.ui.util.res.toStringResource
import com.sevdotdev.seeminglyapokedex.ui.util.theme.toColor

/**
 * Overall Screen for displaying the details of a single pokemon. The screen can exist
 * at the top level of the navigation graph and relies of a ViewModel to hold it's state.
 */
@Composable
fun PokeDetailsScreen(
    viewModel: PokeDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    state.fold(
        isFailure = { error ->
            DefaultErrorContent(
                error = error,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp
                    ),
                onRetryAction = {
                    viewModel.submitAction(RetryFetchPokemon)
                }
            )
        },
        isLoading = {
            DefaultLoadingContent(
                Modifier.fillMaxSize()
            )
        },
        isSuccess = { pokemon ->
            PokeDetailsContent(pokemon)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PokeDetailsContent(
    pokemon: SinglePokemon
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Details header for the pokemon displayed, shows name, type(s), and physical attributes
        stickyHeader {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = pokemon.name.replaceFirstChar {
                    it.uppercase()
                }, style = MaterialTheme.typography.titleLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    pokemon.types.forEach {
                        TypePill(
                            typeName = stringResource(id = it.toStringResource()),
                            typeColor = it.toColor()
                        )
                    }
                }
                Row {
                    Text(text = stringResource(id = R.string.height_feet, pokemon.heightImperial))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = stringResource(id = R.string.weight_lbs, pokemon.weightImperial))
                }
            }
        }

        pokemon.sprite.frontSpriteUrl?.let { front ->
            item {
                SpriteCard(
                    spriteFrontUrl = front,
                    spriteBackUrl = pokemon.sprite.backSpriteUrl,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }

        item {
            BaseStatsCard(
                statsMap = pokemon.stats, pokemonType = pokemon.types, Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        // List of available moves for the displayed pokemon
        item {
            Column {
                Text(
                    text = stringResource(R.string.move_set_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                pokemon.moveSet.forEach { move ->
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = move.name, style = MaterialTheme.typography.headlineSmall)
                        move.learnedAtLevel?.let { lvl ->
                            Text(text = stringResource(R.string.learnable_at_level, lvl))
                        }
                    }
                    Divider()
                }
            }
        }
    }
}