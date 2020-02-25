package com.ccpp.shared.core.result

import com.ccpp.shared.core.exception.Failure
import com.ccpp.shared.core.exception.Failure.ServerError
import com.ccpp.shared.core.result.Either.Left
import com.ccpp.shared.core.result.Either.Right
import com.ccpp.shared.core.result.Results.Success
import retrofit2.Call

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Results<out R> {

    data class Success<out T>(val data: T) : Results<T>()
    data class Error(val exception: Exception) : Results<Nothing>()
    object Loading : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

    companion object {
        fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError())
                }
            } catch (exception: Throwable) {
                Left(ServerError())
            }
        }
    }
}

/**
 * `true` if [Results] is of type [Success] & holds non-null [Success.data].
 */
val Results<*>.succeeded
    get() = this is Success && data != null
