package com.ccpp.shared.network

import com.ccpp.shared.BuildConfig
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateSessionBetReq
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

    fun callLoginWithSocialAsync(token: String, socialMedia: String, emailId: String) =
        apiClient.callLoginWithSocialAsync(token, socialMedia, emailId)

    fun callSignUpAsync(signUpReq: SignUpReq) = apiClient.callSignUpAsync(signUpReq)

    fun callCreateBetAsync(betReq: CreateBetReq) =
        apiClient.callCreateBetAsync(betReq, betReq.contestsId.toString())

    fun callCreateSessionBetAsync(betReq: CreateSessionBetReq) =
        apiClient.callCreateSessionBetAsync(betReq, betReq.contestsId.toString())

    fun callForgetPasswordAsync(email: String) = apiClient.callForgetPasswordAsync(email)

    fun callMatchDetailsAsync(providerId: Int) =
        matchDetailsApiClient.callMatchDetailsAsync(providerId)

    fun callBcCoinsLedgersAsync(page: Int) = apiClient.callBcCoinsLedgersAsync(page)

    fun callUserMatchContestAsync(matchId: String) = apiClient.callUserMatchContestAsync(matchId)

    fun callCreateContestAsync(matchId: String) = apiClient.callCreateContestAsync(matchId)

    fun callPositionDetailsAsync(contestId: Int) = apiClient.callPositionDetailsAsync(contestId)

    fun callMyBetAsync(providerId: String) = apiClient.callMyBetAsync(providerId)

    fun callMatchesListingAsync(event_type: String,play_status: String) = apiClient.callMatchesListingAsync(event_type,play_status)

    fun callWinnerListingAsync(matchId: Int, contestId: Int, page: Int) =
        apiClient.callWinnerListingAsync(contestId, matchId, page)

}
