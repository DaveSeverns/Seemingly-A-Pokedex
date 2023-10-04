package com.sevdotdev.seeminglyapokedex.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.domain.model.PokeError

/**
 * Reusable error content which can be displayed when a [PokeError] occurs and
 * the user should be aware.
 *
 * @param onRetryAction lambda which will be invoked if the retry text is displayed and clicked.
 */
@Composable
fun DefaultErrorContent(
    error: PokeError,
    modifier: Modifier = Modifier,
    onRetryAction: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.something_went_wrong),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
            )
            error.details?.let { deets ->
                Text(
                    text = deets, style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                )
            }
            if (error is PokeError.SomethingWentWrong && onRetryAction != null) {
                Box(modifier = Modifier
                    .clickable {
                        onRetryAction()
                    }
                    .semantics {
                        role = Role.Button
                    }
                    .sizeIn(minHeight = 48.dp, minWidth = 48.dp)) {
                    Text(
                        text = stringResource(R.string.retry_text),
                        color = MaterialTheme.colorScheme.inversePrimary,
                        style = MaterialTheme.typography.labelSmall.copy(
                            textDecoration =
                            TextDecoration.Underline
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}