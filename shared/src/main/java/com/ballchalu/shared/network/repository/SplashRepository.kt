package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.network.ApiService
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {
//    suspend fun getLoginCall(username: String, password: String) = baseRepository.safeApiCall(
//        call = {
//            service.callLoginAsync(username, password).await()
//        },
//        errorMessage = "Error occurred"
//    )

}
