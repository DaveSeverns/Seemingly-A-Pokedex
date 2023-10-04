package com.sevdotdev.seeminglyapokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sevdotdev.seeminglyapokedex.ui.pokedetails.PokeDetailsScreen
import com.sevdotdev.seeminglyapokedex.ui.pokelist.PokeListScreen

/**
 * Wrapper around AndroidX [NavHost] which requires a [Navigator] as an
 * argument.
 */
@Composable
fun PokeDexNavHost(
    navController: NavHostController,
    navigator: Navigator,
) {
    NavHost(navController = navController, startDestination = NavRoute.Home.path){
        composable(NavRoute.PokemonList.path) {
            PokeListScreen(
                navigator
            )
        }

        composable(NavRoute.PokemonDetails.path) {
            PokeDetailsScreen()
        }
    }
}