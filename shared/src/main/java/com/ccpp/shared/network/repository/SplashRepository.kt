package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {
    suspend fun getLoginCall(username: String, password: String) = baseRepository.safeApiCall(
        call = {
            service.callLoginAsync().await()
        },
        errorMessage = "Error occurred"
    )

}
