package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.winner.WinnerRes
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class WinnerRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callWinnerListing(matchId: Int, contestId: Int, page: Int): Results<WinnerRes> =
        baseRepository.safeApiCall(
            call = {
                service.callWinnerListingAsync(matchId, contestId, page).await()
            },
            errorMessage = "Error occurred"
        )
}
