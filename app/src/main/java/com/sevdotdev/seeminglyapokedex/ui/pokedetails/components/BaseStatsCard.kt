package com.sevdotdev.seeminglyapokedex.ui.pokedetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.ui.theme.SeeminglyAPokeDexTheme
import com.sevdotdev.seeminglyapokedex.ui.util.theme.toColor

/**
 * A card component for displaying a pokemon's base stats in a stylized list
 * format.
 */
@Composable
internal fun BaseStatsCard(
    statsMap: Map<String, Int>,
    pokemonType: List<PokemonType>,
    modifier: Modifier = Modifier
) {
    DetailsContentCard(
        cardTitle = stringResource(R.string.base_stats_card_title),
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            statsMap.entries.forEach { entry ->
                StatRow(
                    statName = entry.key,
                    statValue = entry.value.toString(),
                    borderColors = pokemonType.map {
                        it.toColor()
                    },
                )
            }
        }
    }
}

@Composable
private fun StatRow(
    statName: String,
    statValue: String,
    borderColors: List<Color>,
) {
    val borderStroke = BorderStroke(2.dp, Brush.linearGradient(borderColors + Color.Gray))
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(borderStroke)
            .padding(all = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = statName)
        Text(text = statValue)
    }
}

@Preview
@Composable
private fun PreviewBaseStatsCard() {
    SeeminglyAPokeDexTheme {
        Column {
            BaseStatsCard(
                statsMap = mapOf(
                    "HP" to 100,
                    "Defense" to 50
                ), pokemonType = listOf(PokemonType.DARK, PokemonType.FIRE)
            )
        }
    }
}
