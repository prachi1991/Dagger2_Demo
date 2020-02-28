package com.ccpp.shared.network

import com.ccpp.shared.domain.LoginResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiClient {
    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callLoginAsync(): Deferred<Response<LoginResult>>


    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callLoginWithSocialAsync(
        token: String,
        socialMedia: String,
        emailId: String
    ): Deferred<Response<LoginResult>>
}