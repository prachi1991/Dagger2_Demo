package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.LoginRes
import com.ballchalu.shared.domain.SignUpReq
import com.ballchalu.shared.domain.profile.EditProfileReq
import com.ballchalu.shared.domain.user.UserRes
import com.ballchalu.shared.network.ApiService
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

    suspend fun getUserDetails(): Results<UserRes> =
        baseRepository.safeApiCall(
            call = {
                service.callUserAsync().await()
            },
            errorMessage = "Error occurred"
        )

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

    suspend fun callSaveProfile(editProfileReq: EditProfileReq) = baseRepository.safeApiCall(
        call = {
            service.callSaveProfileAsync(editProfileReq).await()
        },
        errorMessage = "Error occurred"
    )

}
