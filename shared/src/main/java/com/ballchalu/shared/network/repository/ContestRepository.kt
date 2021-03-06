package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.user.UserRes
import com.ballchalu.shared.network.ApiService
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
    suspend fun getUserDetails(): Results<UserRes> =
        baseRepository.safeApiCall(
            call = {
                service.callUserAsync().await()
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
