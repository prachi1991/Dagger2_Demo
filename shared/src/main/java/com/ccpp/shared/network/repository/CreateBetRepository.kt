package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateSessionBetReq
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class CreateBetRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callCreateBetAsync(betReq: CreateBetReq) =
        baseRepository.safeApiCall(
            call = {
                service.callCreateBetAsync(betReq).await()
            },
            errorMessage = "Error occurred"
        )

    suspend fun callCretateSessionBetAsync(betReq: CreateSessionBetReq) =
        baseRepository.safeApiCall(
            call = {
                service.callCreateSessionBetAsync(betReq).await()
            },
            errorMessage = "Error occurred"
        )


}
