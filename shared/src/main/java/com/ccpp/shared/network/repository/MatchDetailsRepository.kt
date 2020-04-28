package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.contest.UserMatchContestRes
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.domain.position.PositionRes
import com.ccpp.shared.network.ApiService
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
