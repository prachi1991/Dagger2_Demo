package com.ballchalu.base.retrofit

import com.ballchalu.domain.LoginResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callLoginAsync(): Deferred<Response<LoginResult>>
}