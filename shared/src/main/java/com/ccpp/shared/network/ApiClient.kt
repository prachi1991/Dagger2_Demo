package com.ccpp.shared.network

import com.ccpp.shared.domain.ForgetPassRes
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.LoginResult
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.contest.MatchContestRes
import com.ccpp.shared.domain.contest.UserMatchContestRes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

internal interface ApiClient {
    @POST("oauth/token?grant_type=password")
    fun callLoginAsync(@QueryMap options: Map<String, String>): Deferred<Response<LoginRes>>


    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callLoginWithSocialAsync(
        @Query("token")
        token: String,
        @Query("socialmedia")
        socialMedia: String,
        @Query("email")
        emailId: String
    ): Deferred<Response<LoginResult>>


    @POST("api/v1/sign_up")
    fun callSignUpAsync(@Body signUpReq: SignUpReq): Deferred<Response<LoginRes>>


    @GET("api/v1/passwords/forgot")
    fun callForgetPasswordAsync(@QueryMap emailId: String): Deferred<Response<ForgetPassRes>>

    @GET("ballchalu/api/v1/contests")
    fun callMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<MatchContestRes>>

    @GET("ballchalu/api/v1/user_contests")
    fun callUserMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<UserMatchContestRes>>

    @POST("ballchalu/api/v1/contests/{match_id}/user_contests")
    fun callCretateContestAsync(@Path("match_id") match_id: String): Deferred<Response<CreateContestRes>>

}