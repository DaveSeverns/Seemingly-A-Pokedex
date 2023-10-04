package com.sevdotdev.seeminglyapokedex.ui.util.res

import com.sevdotdev.seeminglyapokedex.R
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType

/**
 * Map the [PokemonType] to a string resource value, allows for localization and
 * control over formatting.
 */
fun PokemonType.toStringResource(): Int = when (this) {
    PokemonType.FIRE -> R.string.fire_type
    PokemonType.WATER -> R.string.water_type
    PokemonType.GRASS -> R.string.grass_type
    PokemonType.NORMAL -> R.string.normal_type
    PokemonType.FIGHTING -> R.string.fighting_type
    PokemonType.FLYING -> R.string.flying_type
    PokemonType.POISON -> R.string.poison_type
    PokemonType.GROUND -> R.string.ground_type
    PokemonType.ROCK -> R.string.rock_type
    PokemonType.BUG -> R.string.bug_type
    PokemonType.GHOST -> R.string.ghost_type
    PokemonType.STEEL -> R.string.steel_type
    PokemonType.ELECTRIC -> R.string.electric_type
    PokemonType.PSYCHIC -> R.string.psychic_type
    PokemonType.ICE -> R.string.ice_type
    PokemonType.DRAGON -> R.string.dragon_type
    PokemonType.DARK -> R.string.dark_type
    PokemonType.FAIRY -> R.string.fairy_type
    PokemonType.UNKNOWN -> R.string.unknown_type
}