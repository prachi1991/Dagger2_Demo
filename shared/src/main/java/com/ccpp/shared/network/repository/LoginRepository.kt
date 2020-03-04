package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {


    suspend fun getLoginCall(query: HashMap<String, String>): Results<LoginRes> =
        baseRepository.safeApiCall(
            call = {
                service.callLoginAsync(query).await()
            },
            errorMessage = "Error occurred"
        ).let {
            if (it is Results.Success)
                sharedPref.token = it.data.access_token
            return it
        }

    suspend fun getLoginWithSocial(token: String, socialMedia: String, emailId: String) =
        baseRepository.safeApiCall(
            call = {
                service.callLoginWithSocialAsync(token, socialMedia, emailId).await()
            },
            errorMessage = "Error occurred"
        )

    suspend fun getSignUpCall(signUpReq: SignUpReq): Results<LoginRes> =
        baseRepository.safeApiCall(
            call = {
                service.callSignUpAsync(signUpReq).await()
            },
            errorMessage = "Error occurred"
        ).let {
            if (it is Results.Success)
                sharedPref.token = it.data.access_token
            return it
        }

    suspend fun getForgetPassword(email: String) = baseRepository.safeApiCall(
        call = {
            service.callForgetPasswordAsync(email).await()
        },
        errorMessage = "Error occurred"
    )
}
