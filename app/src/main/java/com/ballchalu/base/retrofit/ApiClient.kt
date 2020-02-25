package com.ballchalu.base.retrofit

import com.ballchalu.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    private const val REQUEST_TIMEOUT = 60
    private lateinit var retrofit: Retrofit
    private var okHttpClient: OkHttpClient? = null
    val client: Retrofit
        get() {
            if (okHttpClient == null) initOkHttp()
            if (!ApiClient::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.baseUrl)
                    .client(okHttpClient!!)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            }
            return retrofit
        }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.apiKey)
                .build()
            val request = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .url(url).build()

            chain.proceed(request)
        })
        okHttpClient = httpClient.build()
    }


}