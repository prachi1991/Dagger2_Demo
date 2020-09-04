package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.winner.WinnerRes
import com.ballchalu.shared.network.ApiService
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
