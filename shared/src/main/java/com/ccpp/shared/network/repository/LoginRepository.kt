package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {


    suspend fun getLoginCall(query: HashMap<String, String>): Results<LoginRes> {
        val result = baseRepository.safeApiCall(
            call = { service.callLoginAsync(query).await() },
            errorMessage = "Error occurred"
        )
        if (result is Results.Success)
            sharedPref.token = result.data.access_token
        return result
    }

    suspend fun getLoginWithSocial(token: String, socialMedia: String, emailId: String) =
        baseRepository.safeApiCall(
            call = {
                service.callLoginWithSocialAsync(token, socialMedia, emailId).await()
            },
            errorMessage = "Error occurred"
        )

    suspend fun getSignUpCall(username: String, password: String) = baseRepository.safeApiCall(
        call = {
            service.callSignUpAsync(username, password).await()
        },
        errorMessage = "Error occurred"
    )

    suspend fun getForgetPassword(username: String) = baseRepository.safeApiCall(
        call = {
            service.callForgetPasswordAsync(username).await()
        },
        errorMessage = "Error occurred"
    )
}
