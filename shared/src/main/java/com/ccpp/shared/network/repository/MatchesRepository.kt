package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.MatchListingReq
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.network.ApiService
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
