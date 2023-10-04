package com.sevdotdev.seeminglyapokedex.dataimpl.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.sevdotdev.seeminglyapokedex.PokemonByNameQuery
import com.sevdotdev.seeminglyapokedex.PokemonListQuery
import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonRemoteDataSource
import com.sevdotdev.seeminglyapokedex.dataimpl.mappers.toDomain
import com.sevdotdev.seeminglyapokedex.domain.model.PokemonListItem
import com.sevdotdev.seeminglyapokedex.domain.model.SinglePokemon
import javax.inject.Inject

/**
 * Implementation of [PokemonRemoteDataSource] for specific use of the [ApolloClient] from
 * the Apollo networking library for interfacing with a graphql backend.
 *
 * Use of [runCatching] will automatically return a Result of the provided type and a failure
 * if any exceptions are thrown.
 *
 * @see /app/src/main/graphql for Apollo configuration details
 * @see com.sevdotdev.seeminglyapokedex.dataimpl.mappers for mapping of generated classes to domain
 * objects
 */
class ApolloPokemonRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : PokemonRemoteDataSource {

    override suspend fun getAllPokemon(): Result<List<PokemonListItem>> = runCatching {
        apolloClient.query(
            PokemonListQuery(
                Optional.present(0),
                Optional.present(151)
            )
        ).execute().dataAssertNoErrors.toDomain()
    }

    override suspend fun getPokemonByName(name: String): Result<SinglePokemon?> = runCatching {
        apolloClient.query(
            PokemonByNameQuery(
                name
            )
        ).execute().dataAssertNoErrors.toDomain()
    }
}