package com.ballchalu.base

import com.ballchalu.utils.AppConstants
import com.ballchalu.utils.extension.Result
import retrofit2.Response
import java.io.IOException


open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> = try {
        val response = call.invoke()
        if (response.isSuccessful && response.code() == AppConstants.STATUS_SUCCESS) {
            Result.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            Result.Error(IOException(response.errorBody()?.toString()))
        } else {
            Result.Error(IOException("Something error..."))
        }
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }

    val <T> T.exhaustive: T get() = this
}