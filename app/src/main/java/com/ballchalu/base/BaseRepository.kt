package com.ballchalu.base

import com.ballchalu.utils.AppConstants
import com.ccpp.shared.core.result.Results
import retrofit2.Response
import java.io.IOException


open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Results<T> = try {
        val response = call.invoke()
        if (response.isSuccessful && response.code() == AppConstants.STATUS_SUCCESS) {
            Results.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            Results.Error(IOException(response.errorBody()?.toString()))
        } else {
            Results.Error(IOException("Something error..."))
        }
    } catch (e: Exception) {
        Results.Error(IOException(errorMessage, e))
    }

    val <T> T.exhaustive: T get() = this
}