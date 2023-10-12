package com.sevdotdev.seeminglyapokedex.dataimpl.mappers

import com.sevdotdev.seeminglyapokedex.PokemonListQuery
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem

/**
 * Map [PokemonListQuery.Data] to a domain object list, if no data is available
 * return an empty list by default.
 */
fun PokemonListQuery.Data?.toDomain(): List<PokemonListItem> {
    return this?.pokemons?.results?.mapNotNull{ result ->
        result?.toDomain()
    } ?: emptyList()
}

/**
 * Map [PokemonListQuery.Result] to domain object, use default values where API response returns
 * null.
 */
fun PokemonListQuery.Result.toDomain(): PokemonListItem = PokemonListItem(
    name = name.orEmpty(),
    id = id?.toString().orEmpty(),
    imageUrl = image.orEmpty()
)