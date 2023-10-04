package com.sevdotdev.seeminglyapokedex.ui.navigation

import androidx.navigation.NavController

/**
 * Default implementation of [Navigator] which makes use of the
 * [NavController] to perform navigation.
 */
class DefaultNavigator(
    private val navController: NavController
) : Navigator {
    override fun navigateTo(route: NavRoute, pathSegments: List<String>?) {
        val destination = if (pathSegments.isNullOrEmpty()) {
            route.path
        } else {
            route.path(*pathSegments.toTypedArray())
        }
        navController.navigate(destination)
    }
}