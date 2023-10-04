package com.sevdotdev.seeminglyapokedex.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Default loading indicator to be displayed, simple infintely spinning circle progress
 * indicator, will display in the center of the given area.
 */
@Composable
fun DefaultLoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier){
        CircularProgressIndicator(
            Modifier.align(Alignment.Center)
        )
    }
}