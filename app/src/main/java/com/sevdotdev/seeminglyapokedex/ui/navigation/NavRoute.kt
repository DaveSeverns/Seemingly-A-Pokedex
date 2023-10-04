package com.sevdotdev.seeminglyapokedex.ui.navigation

/**
 * Sealed class representing different navigation destination in the app.
 */
sealed class NavRoute(protected val basePath: String) {
    abstract val path: String

    /**
     * Leaving this open to allow for overriding in future routes if needed.
     */
    open fun path(vararg segments: String): String {
        if (path == basePath) return path
        return basePath + segments.joinToString(prefix = "/", separator = "/")
    }

    /**
     * Having a defined home route allows for a quick sway of the home destination as the app
     * evolves in the future with out every other area of the code referencing it needing to change.
     *
     * To update the destination simply update the "calculated" return value for the [path]
     */
    object Home : NavRoute("home") {
        override val path: String
            get() = PokemonList.path
    }

    object PokemonList : NavRoute("pokemon-list") {
        override val path: String
            get() = this.basePath
    }

    object PokemonDetails : NavRoute("pokemon-details") {

        const val ARG_NAME = "pokemonName"

        override val path: String
            get() = "$basePath/{$ARG_NAME}"
    }


}