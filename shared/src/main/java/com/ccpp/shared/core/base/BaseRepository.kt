package com.ccpp.shared.core.base

import com.ccpp.shared.core.result.Results
import com.ccpp.shared.util.ConstantsBase.STATUS_SUCCESS
import retrofit2.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Results<T> = try {
        val response = call.invoke()
        if (response.isSuccessful && response.code() == STATUS_SUCCESS) {
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