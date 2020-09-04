package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.domain.create_bet.CreateBetReq
import com.ballchalu.shared.domain.create_bet.CreateSessionBetReq
import com.ballchalu.shared.network.ApiService
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
