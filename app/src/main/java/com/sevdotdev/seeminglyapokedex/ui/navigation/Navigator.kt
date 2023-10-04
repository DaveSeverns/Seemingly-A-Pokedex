package com.sevdotdev.seeminglyapokedex.ui.navigation

/**
 * Abstraction around navigation function within the application. This abstraction allows
 * @Composable screens and other components which may require some access to navigation instructions
 * to only be dependent on the interface and continue to use the [NavRoute] regardless if the
 * compose navigation implmentation changes.
 */
interface Navigator {
    fun navigateTo(route: NavRoute, pathSegments: List<String>? = null)
}