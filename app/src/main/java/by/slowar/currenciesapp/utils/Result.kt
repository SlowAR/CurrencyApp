package by.slowar.currenciesapp.utils

import java.util.concurrent.CancellationException

sealed class Result<out S, out E> {

    data class Success<out S>(val result: S) : Result<S, Nothing>()

    data class Error<out E>(val error: E) : Result<Nothing, E>()
}

fun <S, E : Throwable> Result<S, E>.getOrThrow(): S? {
    return when (this) {
        is Result.Error -> throw this.error
        is Result.Success -> this.result
    }
}

inline fun <S, R> S.executeCatching(block: S.() -> R): Result<R, Throwable> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.Error(e)
    }
}

inline fun <S, E, R> Result<S, E>.mapSuccess(block: (S) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(block(this.result))
        is Result.Error -> Result.Error(this.error)
    }
}

inline fun <S, E> Result<S, E>.doOnSuccess(block: (S) -> Unit): Result<S, E> {
    if (this is Result.Success) {
        block(this.result)
    }
    return this
}