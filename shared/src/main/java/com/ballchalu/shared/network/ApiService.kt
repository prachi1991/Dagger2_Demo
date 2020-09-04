package com.ballchalu.shared.network

import com.ballchalu.shared.BuildConfig
import com.ballchalu.shared.domain.SignUpReq
import com.ballchalu.shared.domain.create_bet.CreateBetReq
import com.ballchalu.shared.domain.create_bet.CreateSessionBetReq
import com.ballchalu.shared.domain.profile.ChangePasswordReq
import com.ballchalu.shared.domain.profile.EditProfileReq
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiService @Inject constructor(
    builder: Retrofit.Builder
) {


    private val apiClient =
        builder.baseUrl(BuildConfig.baseUrl)
            .build().create(ApiClient::class.java)


    private val matchDetailsApiClient =
        builder.baseUrl(BuildConfig.matchDetailsUrl)
            .build().create(ApiClient::class.java)


    fun callLoginAsync(query: HashMap<String, String>) =
        apiClient.callLoginAsync(query)

    fun callUserAsync() = apiClient.callUserAsync()

    fun callBuyNowCoinsAsync(id: Int) = apiClient.callBuyNowCoinsAsync(id)

    fun callLoginWithSocialAsync(token: String, socialMedia: String, emailId: String) =
        apiClient.callLoginWithSocialAsync(token, socialMedia, emailId)

    fun callSignUpAsync(signUpReq: SignUpReq) = apiClient.callSignUpAsync(signUpReq)

    fun callCreateBetAsync(betReq: CreateBetReq) =
        apiClient.callCreateBetAsync(betReq, betReq.contestsId.toString())

    fun callCreateSessionBetAsync(betReq: CreateSessionBetReq) =
        apiClient.callCreateSessionBetAsync(betReq, betReq.contestsId.toString())

    fun callMatchContestAsync(matchId: String) = apiClient.callMatchContestAsync(matchId)

    fun callForgetPasswordAsync(email: String) = apiClient.callForgetPasswordAsync(email)

    fun callMatchDetailsAsync(providerId: Int) =
        matchDetailsApiClient.callMatchDetailsAsync(providerId)

    fun callChangePasswordAsync(changePasswordReq: ChangePasswordReq) =
        apiClient.callChangePasswordAsync(changePasswordReq)

    fun callBcCoinsLedgersAsync(page: Int) = apiClient.callBcCoinsLedgersAsync(page)

    fun callBcCoinsListAsync() = apiClient.callBcCoinsListAsync()

    fun callUserMatchContestAsync(matchId: String) = apiClient.callUserMatchContestAsync(matchId)

    fun callCreateContestAsync(matchId: String) = apiClient.callCreateContestAsync(matchId)

    fun callPositionDetailsAsync(contestId: Int) = apiClient.callPositionDetailsAsync(contestId)

    fun callUserContestAsync(contestId: Int) = apiClient.callUserContestAsync(contestId)

    fun callMyBetAsync(providerId: String) = apiClient.callMyBetAsync(providerId)

    fun callMatchesListingAsync(event_type: String,play_status: String) = apiClient.callMatchesListingAsync(event_type,play_status)

    fun callWinnerListingAsync(matchId: Int, contestId: Int, page: Int) =
        apiClient.callWinnerListingAsync(contestId, matchId, page)

    fun callSaveProfileAsync(editProfileReq: EditProfileReq) =
        apiClient.callSaveProfileAsync(editProfileReq)


}
