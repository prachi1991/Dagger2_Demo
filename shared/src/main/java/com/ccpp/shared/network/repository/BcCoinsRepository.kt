package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class BcCoinsRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callBcCoinsLedgersAsync(page: Int) = baseRepository.safeApiCall(
        call = {
            service.callBcCoinsLedgersAsync(page).await()
        },
        errorMessage = "Error occurred"
    )

    suspend fun callBcCoinsListAsync() = baseRepository.safeApiCall(
        call = {
            service.callBcCoinsListAsync().await()
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

    suspend fun callBuyNowCoins(id: Int) =
        baseRepository.safeApiCall(
            call = {
                service.callBuyNowCoinsAsync(id).await()
            },
            errorMessage = "Error occurred"
        )
}
