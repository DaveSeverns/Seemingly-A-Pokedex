package com.sevdotdev.seeminglyapokedex.domain.model

import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType.UNKNOWN

/**
 * Domain representation of  Pokemon list item to be displayed in a high level
 * list of pokemon.
 */
data class PokemonListItem(
    val name: String,
    val imageUrl: String,
    val id: String,
    val type: PokemonType = UNKNOWN
)
