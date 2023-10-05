package com.sevdotdev.seeminglyapokedex.ui.pokedetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.ui.theme.SeeminglyAPokeDexTheme

/**
 * A card component for displaying sprite details and images for
 * pokemon
 */
@Composable
internal fun SpriteCard(
    spriteFrontUrl: String,
    spriteBackUrl: String?,
    modifier: Modifier = Modifier
) {
    DetailsContentCard(cardTitle = stringResource(R.string.sprite_title), modifier = modifier) {
        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SpriteImage(
                    label = stringResource(R.string.sprite_front_label),
                    imageUrl = spriteFrontUrl
                )
                spriteBackUrl?.let { 
                    SpriteImage(label = stringResource(R.string.sprite_back_label), imageUrl = it)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SpriteImage(
    label: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                .crossfade(true).placeholder(R.drawable.pokeball_ic).build()
        )
        Image(painter = painter, contentDescription = null, modifier = Modifier
            .clip(
                CircleShape
            )
            .size(100.dp)
            .background(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
private fun PreviewSpriteCard() {
    SeeminglyAPokeDexTheme {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            SpriteCard(spriteFrontUrl = "", spriteBackUrl = "")
            SpriteCard(spriteFrontUrl = "", spriteBackUrl = null)
        }
    }
}
