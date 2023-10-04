package com.sevdotdev.seeminglyapokedex.domain.model

/**
 * Domain object representing more details of a single pokemon
 */
data class SinglePokemon(
    val name: String,
    val heightImperial: Int,
    val weightImperial: Int,
    val types: List<PokemonType>,
    val moveSet: Set<Move>,
    val stats: Map<String, Int>,
    val sprite: Sprite,
)

/**
 * Models a move in a pokemon's move set
 */
data class Move(
    val name: String,
    val learnedAtLevel: Int? = null,
)

/**
 * Model to wrap data needed to display variations of a pokemon's sprites
 */
data class Sprite(
    val frontSpriteUrl: String?,
    val backSpriteUrl: String?
)