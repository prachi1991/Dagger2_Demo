package com.ccpp.shared.network.repository

import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.profile.ChangePasswordReq
import com.ccpp.shared.network.ApiService
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
