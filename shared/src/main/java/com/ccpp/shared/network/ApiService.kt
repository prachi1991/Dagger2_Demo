package com.ccpp.shared.network

import com.ccpp.shared.BuildConfig
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.util.ConstantsBase.REQUEST_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    fun callLoginWithSocialAsync(token: String, socialMedia: String, emailId: String) =
        apiClient.callLoginWithSocialAsync(token, socialMedia, emailId)

    fun callSignUpAsync(signUpReq: SignUpReq) = apiClient.callSignUpAsync(signUpReq)

    fun callCretateBetAsync(betReq: CreateBetReq) = apiClient.callCretateBetAsync(betReq,betReq.contestsId.toString())

    fun callForgetPasswordAsync(email: String) = apiClient.callForgetPasswordAsync(email)

    fun callMatchDetailsAsync(matchId: Int) = matchDetailsApiClient.callMatchDetailsAsync(matchId)

    fun callMatchContestAsync(matchId: String) = apiClient.callMatchContestAsync(matchId)

    fun callUserMatchContestAsync(matchId: String) = apiClient.callUserMatchContestAsync(matchId)

    fun callCretateContestAsync(matchId: String) = apiClient.callCretateContestAsync(matchId)

    fun callMatchesListingAsync(event_type: String,play_status: String) = apiClient.callMatchesListingAsync(event_type,play_status)
}
