package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateBetRes
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class CreateBetRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {

    suspend fun callCretateBetAsync(betReq: CreateBetReq): Results<CreateBetRes> =
        baseRepository.safeApiCall(
            call = {
                service.callCretateBetAsync(betReq).await()
            },
            errorMessage = "Error occurred"
        )


}
