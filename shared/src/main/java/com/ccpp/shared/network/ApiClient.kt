package com.ccpp.shared.network

import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.LoginResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

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

    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callSignUpAsync(

    ): Deferred<Response<LoginResult>>

    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callForgetPasswordAsync(): Deferred<Response<LoginResult>>

}