package com.sevdotdev.seeminglyapokedex.dataimpl.mappers

import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery.Pokemon
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery.Sprites
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery.Stat
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery.Type
import com.sevdotdev.seeminglyapokedex.domain.model.Move
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonType
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import com.sevdotdev.seeminglyapokedex.domain.model.Sprite

/**
 * Map [PokemonByNameQuery.Data] to domain object, nullable return type allowed.
 */
internal fun PokemonByNameQuery.Data?.toDomain(): SinglePokemon? =
    this?.pokemon?.toDomain()

/**
 * Map [PokemonByNameQuery.Pokemon] to domain object, provide defaults,
 * if pokemon's name is not present data is invalid and return `null`
 */
internal fun Pokemon.toDomain(): SinglePokemon? =
    SinglePokemon(
        name = this.name.orEmpty(),
        heightImperial = this.height ?: 0,
        weightImperial = this.weight ?: 0,
        types = this.types.toDomain(),
        moveSet = this.moves.toDomain(),
        stats = this.stats.toDomain(),
        sprite = this.sprites.toDomain()
    ).takeIf { it.name.isNotEmpty() }


internal fun List<Type?>?.toDomain(): List<PokemonType> =
    this?.map { queryType ->
        getPokemonTypeFromString(queryType?.type?.name)
    } ?: listOf(PokemonType.UNKNOWN)


internal fun List<Stat?>?.toDomain(): Map<String, Int> {
    return this?.mapNotNull {
        it?.stat?.name?.let { name ->
            Pair(name, it.base_stat ?: 0)
        }
    }?.toMap() ?: emptyMap()
}

internal fun Sprites?.toDomain() = Sprite(
    this?.front_default,
    this?.back_default
)

internal fun List<PokemonByNameQuery.Move?>?.toDomain(): Set<Move> {
    val initialSet = mutableSetOf<Move>()
    this?.map { queryMove ->
        queryMove?.move?.name?.let { name ->
            queryMove.version_group_details?.firstOrNull {
                it?.version_group?.name == "yellow"
            }?.level_learned_at?.let { lvl ->
                initialSet.add(Move(name = name, learnedAtLevel = lvl))
            }
        }
    }
    return initialSet
}

private fun getPokemonTypeFromString(typeString: String?): PokemonType {
    return if (typeString != null) {
        PokemonType.valueOf(typeString.uppercase())
    } else {
        PokemonType.UNKNOWN
    }
}


