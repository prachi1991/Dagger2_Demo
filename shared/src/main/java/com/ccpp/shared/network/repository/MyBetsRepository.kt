package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class MyBetsRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callMyBetAsync(providerId: String) = baseRepository.safeApiCall(
        call = {
            service.callMyBetAsync(providerId).await()
        },
        errorMessage = "Error occurred"
    )
}
