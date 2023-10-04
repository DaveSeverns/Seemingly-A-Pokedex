package com.sevdotdev.seeminglyapokedex.domain.model

/**
 * Sealed class to represent errors specific to the domain and allow for more granular approach
 * to handling them in the UI layer than checking specific [Exception] types.
 *
 * @property details optional additional details which can be provided.
 */
sealed class PokeError(val details: String? = null) {
    object InvalidData: PokeError()
    data class SomethingWentWrong(val errorMessage: String?): PokeError(errorMessage)
}
