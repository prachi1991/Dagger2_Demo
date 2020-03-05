package com.ccpp.shared.network

import com.ccpp.shared.BuildConfig
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.SignUpReq
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
open class ApiService @Inject constructor(private val sharedPref: SharedPreferenceStorage) {

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
//                    .addQueryParameter("api_key", )
                    .build()
                val request = original.newBuilder()
                    .addHeader("Api-Key", BuildConfig.apiKey)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer "+ sharedPref.token.toString())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .url(url).build()

                chain.proceed(request)
            })
            return httpClient.build()
        }

    fun callLoginAsync(query: HashMap<String, String>) =
        apiClient.callLoginAsync(query)

    fun callLoginWithSocialAsync(token: String, socialMedia: String, emailId: String) =
        apiClient.callLoginWithSocialAsync(token, socialMedia, emailId)

    fun callSignUpAsync(signUpReq: SignUpReq) = apiClient.callSignUpAsync(signUpReq)

    fun callForgetPasswordAsync(email: String) = apiClient.callForgetPasswordAsync(email)

    fun callMatchContestAsync(matchId: String) = apiClient.callMatchContestAsync(matchId)

    fun callUserMatchContestAsync(matchId: String) = apiClient.callUserMatchContestAsync(matchId)

    fun callCretateContestAsync(matchId: String) = apiClient.callCretateContestAsync(matchId)
}
