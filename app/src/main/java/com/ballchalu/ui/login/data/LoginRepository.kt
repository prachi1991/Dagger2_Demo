package com.ballchalu.ui.login.data

import com.ballchalu.base.BaseRepository
import com.ballchalu.base.retrofit.ApiClient
import com.ballchalu.base.retrofit.ApiService

class LoginRepository : BaseRepository() {


    suspend fun getLoginCall(username: String, password: String) = safeApiCall(
        call = {
            ApiClient.client.create(
                ApiService::class.java
            ).callLoginAsync().await()
        },
        errorMessage = "Error occurred"
    )

}
