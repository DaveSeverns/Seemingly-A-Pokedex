package com.sevdotdev.seeminglyapokedex.ui.util.theme

import androidx.compose.ui.graphics.Color
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.ui.theme.PokemonColors

/**
 * Map [PokemonType] to a themed color to allow for more stylized UI components when viewing
 * a pokemon of that specific type.
 */
fun PokemonType.toColor(): Color = when (this) {
    PokemonType.FIRE -> PokemonColors.fire
    PokemonType.WATER -> PokemonColors.water
    PokemonType.GRASS -> PokemonColors.grass
    PokemonType.NORMAL -> PokemonColors.normal
    PokemonType.FIGHTING -> PokemonColors.fighting
    PokemonType.FLYING -> PokemonColors.flying
    PokemonType.POISON -> PokemonColors.poison
    PokemonType.GROUND -> PokemonColors.ground
    PokemonType.ROCK -> PokemonColors.rock
    PokemonType.BUG -> PokemonColors.bug
    PokemonType.GHOST -> PokemonColors.ghost
    PokemonType.STEEL -> PokemonColors.steel
    PokemonType.ELECTRIC -> PokemonColors.electric
    PokemonType.PSYCHIC -> PokemonColors.psychic
    PokemonType.ICE -> PokemonColors.ice
    PokemonType.DRAGON -> PokemonColors.dragon
    PokemonType.DARK -> PokemonColors.dark
    PokemonType.FAIRY -> PokemonColors.fairy
    PokemonType.UNKNOWN -> PokemonColors.unknown
}