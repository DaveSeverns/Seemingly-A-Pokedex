package com.sevdotdev.seeminglyapokedex.domain.model

/**
 * Ubiquitous result class which allows for wrapping the result of a request in one of three
 * specific implementations of [PokeDataResult] representing the state of the request
 *
 * [Success] self explanatory, wraps the  requested data of type [T]
 * [Failure] request has failed, the reason being a [PokeError]
 * [Loading] an object, representing work still in progress.
 */
sealed class PokeDataResult<out T> {
    data class Success<out T>(val data: T) : PokeDataResult<T>()
    data class Failure(val error: PokeError) : PokeDataResult<Nothing>()
    object Loading : PokeDataResult<Nothing>()
}

/**
 * Simplify handling a [PokeDataResult] based on which
 * implementation the object is.
 *
 * function is `inlined` to allow for calling of a @Composable function, provided it is called
 * in an composable scope.
 */
inline fun <R, reified T> PokeDataResult<T>.fold(
    isSuccess: (data: T) -> R,
    isFailure: (exception: PokeError) -> R,
    isLoading: () -> R,
): R {
    return when (this) {
        is PokeDataResult.Failure -> {
            isFailure(this.error)
        }

        PokeDataResult.Loading -> {
            isLoading()
        }

        is PokeDataResult.Success -> {
            isSuccess(this.data)
        }
    }
}
