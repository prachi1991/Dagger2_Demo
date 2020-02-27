package com.ccpp.shared.network

import com.ccpp.shared.BuildConfig
import com.ccpp.shared.util.ConstantsBase.REQUEST_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiService
@Inject constructor(
) {

    //    private lateinit var retrofit: Retrofit
//
//        val client: Retrofit
//        get() {
//            if (okHttpClient == null) initOkHttp()
//            if (!::retrofit.isInitialized) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BuildConfig.baseUrl)
//                    .client(okHttpClient!!)
//                    .addConverterFactory(MoshiConverterFactory.create())
//                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                    .build()
//            }
//            return retrofit
//        }
    private val apiClient = Retrofit.Builder()
        .baseUrl(BuildConfig.baseUrl)
        .client(initOkHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(ApiClient::class.java)

    private val initOkHttp: OkHttpClient
        get() {
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
            return httpClient.build()
        }

    fun callLoginAsync(username: String, password: String) =
        apiClient.callLoginAsync()

}
