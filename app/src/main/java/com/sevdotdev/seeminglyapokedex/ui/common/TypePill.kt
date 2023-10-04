package com.sevdotdev.seeminglyapokedex.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sevdotdev.seeminglyapokedex.ui.theme.SeeminglyAPokeDexTheme


/**
 * Reusable "Pill" card to display the Pokemon's type in a stylized format
 *
 * As this component is intended to be used across the app leaving the interface (parameters)
 * low level is an intentional choice. This is favored instead of passing
 * [com.sevdotdev.seeminglyapokedex.domain.model.PokemonType] directly
 */
@Composable
fun TypePill(
    typeName: String,
    typeColor: Color,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(1.dp, Brush.horizontalGradient(colors = listOf(typeColor, Color.Gray)))
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text(text = typeName, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}



@Preview
@Composable
private fun PreviewTypePill(

) {
    SeeminglyAPokeDexTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(20.dp)) {
            TypePill(typeName = "Fire", typeColor = Color.Red)
        }
    }
}