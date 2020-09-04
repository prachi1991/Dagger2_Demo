package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.contest.CreateContestRes
import com.ballchalu.shared.domain.contest.UserMatchContestRes
import com.ballchalu.shared.domain.match_details.MatchDetailsRes
import com.ballchalu.shared.domain.position.PositionRes
import com.ballchalu.shared.network.ApiService
import javax.inject.Inject

class MatchDetailsRepository @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {


    suspend fun callMatchDetailsAsync(providerId: Int): Results<MatchDetailsRes> =
        baseRepository.safeApiCall(
            call = {
                service.callMatchDetailsAsync(providerId).await()
            },
            errorMessage = "Error occurred"
        )

    suspend fun callPositionDetailsAsync(contestId: Int): Results<PositionRes> =
        baseRepository.safeApiCall(
            call = {
                service.callPositionDetailsAsync(contestId).await()
            },
            errorMessage = "Error occurred"
        )

    suspend fun callUserContestAsync(contestId: Int): Results<CreateContestRes> =
        baseRepository.safeApiCall(
            call = {
                service.callUserContestAsync(contestId).await()
            },
            errorMessage = "Error occurred"
        )


}
