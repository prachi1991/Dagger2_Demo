package com.ballchalu.shared.network.repository

import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.profile.ChangePasswordReq
import com.ballchalu.shared.network.ApiService
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val service: ApiService,
    private val baseRepository: BaseRepository
) {


    suspend fun callChangePasswordAsync(changePasswordReq: ChangePasswordReq) =
        baseRepository.safeApiCall(
            call = {
                service.callChangePasswordAsync(changePasswordReq).await()
            },
            errorMessage = "Error occurred"
        )


}
