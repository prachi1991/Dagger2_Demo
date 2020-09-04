package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.MatchListingReq
import com.ballchalu.shared.domain.MatchListingRes
import com.ballchalu.shared.network.ApiService
import javax.inject.Inject

class MatchesRepository @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun getMatchesListing(event_type: String,play_status: String): Results<MatchListingRes> =
        baseRepository.safeApiCall(
            call = {
                service.callMatchesListingAsync(event_type,play_status).await()
            },
            errorMessage = "Error occurred"
        )
}
