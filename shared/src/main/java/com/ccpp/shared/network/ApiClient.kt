package com.ccpp.shared.network

import com.ccpp.shared.domain.ForgetPassRes
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.LoginResult
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.match_details.MatchDetailsRes
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


    @GET("api/v1/matches/{matchId}/user_match_show.json")
    fun callMatchDetailsAsync(@Path("matchId") matchId: Int): Deferred<Response<MatchDetailsRes>>



}