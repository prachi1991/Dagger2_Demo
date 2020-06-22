package com.ccpp.shared.core.base

import com.ccpp.shared.core.exception.NotFoundException
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.rxjava.RxBus
import com.ccpp.shared.util.ConstantsBase
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepository @Inject constructor() {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Results<T> = try {
        val response = call.invoke()
        if (response.isSuccessful && response.code() == ConstantsBase.STATUS_SUCCESS) {
            Results.Success(response.body()!!)
        } else if (response.code() == ConstantsBase.INTERNAL_ERROR) {
            Results.Error(IOException("Internal Server Error"))
        } else if (response.code() == ConstantsBase.NOT_FOUND && response.errorBody() != null) {
            Results.Error(
                NotFoundException(
                    ConstantsBase.NOT_FOUND,
                    response.errorBody()?.toString()
                )
            )
        } else if (response.code() == ConstantsBase.TOKEN_EXPIRED) {
            RxBus.publish(ConstantsBase.TOKEN_EXPIRED)
            Results.Error(IOException("Session Expired"))
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