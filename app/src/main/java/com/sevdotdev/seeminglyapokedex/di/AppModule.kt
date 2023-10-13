package com.sevdotdev.seeminglyapokedex.di

import com.apollographql.apollo3.ApolloClient
import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonLocalDataSource
import com.sevdotdev.seeminglyapokedex.data.datasource.PokemonRemoteDataSource
import com.sevdotdev.seeminglyapokedex.data.repository.PokemonRepository
import com.sevdotdev.seeminglyapokedex.dataimpl.datasource.ApolloPokemonRemoteDataSource
import com.sevdotdev.seeminglyapokedex.dataimpl.datasource.InMemoryPokemonLocalDataSource
import com.sevdotdev.seeminglyapokedex.dataimpl.repository.DefaultPokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://graphql-pokeapi.vercel.app/api/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun providesPokemonLocalDataSource(
        inMemoryPokemonLocalDataSource: InMemoryPokemonLocalDataSource
    ): PokemonLocalDataSource = inMemoryPokemonLocalDataSource

    @Provides
    fun providesPokemonRemoteDataSource(
        apolloPokemonRemoteDataSource: ApolloPokemonRemoteDataSource
    ): PokemonRemoteDataSource = apolloPokemonRemoteDataSource

    @Provides
    fun providesPokemonRepository(
        defaultPokemonRepository: DefaultPokemonRepository
    ): PokemonRepository = defaultPokemonRepository

    /**
     * Provide a worker coroutine scope for data layer IO which can be injected to allow for
     * ease of testing.
     */
    @Provides
    fun providesIOWorkScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
}
