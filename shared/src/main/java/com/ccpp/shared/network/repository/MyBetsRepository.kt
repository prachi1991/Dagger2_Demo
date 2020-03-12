package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class MyBetsRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callMyBetAsync(matchId: String) = baseRepository.safeApiCall(
        call = {
            service.callMyBetAsync(matchId).await()
        },
        errorMessage = "Error occurred"
    )
}
