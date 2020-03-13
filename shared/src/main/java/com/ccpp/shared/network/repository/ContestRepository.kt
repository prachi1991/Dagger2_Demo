package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class ContestRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun getMatchContest(matchId: String) = baseRepository.safeApiCall(
        call = {
            service.callMatchContestAsync(matchId).await()
        },
        errorMessage = "Error occurred"
    )

    suspend fun getUserMatchContest(matchId: String) = baseRepository.safeApiCall(
        call = {
            service.callUserMatchContestAsync(matchId).await()
        },
        errorMessage = "Error occurred"
    )

    suspend fun createUserMatchContest(matchId: String) = baseRepository.safeApiCall(
        call = {
            service.callCreateContestAsync(matchId).await()
        },
        errorMessage = "Error occurred"
    )
}
