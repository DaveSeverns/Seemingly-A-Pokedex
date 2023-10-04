package com.sevdotdev.seeminglyapokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sevdotdev.seeminglyapokedex.ui.navigation.DefaultNavigator
import com.sevdotdev.seeminglyapokedex.ui.navigation.PokeDexNavHost
import com.sevdotdev.seeminglyapokedex.ui.theme.SeeminglyAPokeDexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeeminglyAPokeDexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    PokeDexNavHost(
                        navController = navController,
                        navigator = DefaultNavigator(navController)
                    )
                }
            }
        }
    }
}
