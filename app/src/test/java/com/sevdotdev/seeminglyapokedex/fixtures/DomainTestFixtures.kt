package com.sevdotdev.seeminglyapokedex.fixtures

import com.sevdotdev.seeminglyapokedex.domain.model.Move
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.domain.model.Sprite

/**
 * Reusable fixtures to be used for test which require domain models to run/verify results
 * against
 */
object DomainTestFixtures {
    fun createSinglePokemon(
        name: String = Defaults.DEFAULT_NAME,
        height: Int = 6,
        weight: Int = 25,
        types: List<PokemonType> = listOf(PokemonType.FIRE),
        moves: Set<Move> = setOf(Move("flamethrower", null)),
        stats:Map<String, Int> = mapOf("speed" to 60),
    )= SinglePokemon(
        name = name,
        weightImperial = weight,
        heightImperial = height,
        types = types,
        sprite = Sprite(null, null),
        moveSet = moves,
        stats = stats,
    )

    fun createPokemonListItem(
        name: String = Defaults.DEFAULT_NAME,
        id: String = "5",
        imageUrl: String = "url"
    ) = PokemonListItem(
        name = name,
        imageUrl = imageUrl,
        id = id,
    )

    object Defaults {
        const val DEFAULT_NAME = "charmander"
    }
}