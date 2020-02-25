package com.ccpp.shared.network

import android.util.Log
import com.ccpp.shared.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class ApiServiceForAccessToken {


    private fun getSecret(sessions: Sessions): String {
        /*var clintID = ""
        var clintSecret = ""
        try {
            clintID =
                AESUtils().decrypt(BuildConfig.CLIENT_ID, sessions.appBundle, "a5gb0dl5mdleoy53")
            clintSecret = AESUtils().decrypt(
                BuildConfig.CLIENT_SECRET,
                sessions.appBundle,
                "a5gb0dl5mdleoy53"
            )
        } catch (e: Exception) {

        }
        println("EEEEE " + clintID)
        println("EEEEE " + clintSecret)
        return Base64.encodeToString(
            ("$clintID:$clintSecret").toByteArray(),
            Base64.NO_WRAP
        )*/
        return ""
    }


    private fun getAppControllerSecret(): String {

        if (BuildConfig.DEBUG) {
            return "OHBhcWFxZzhlYjNrMTBhMXY5ZjRrcmkyZToxZWRsZXBnMmxxYjhsdjg0cWJjZGZsM3UzcDE2a3EyZTYxc25va2NubnFtczBpaDlpajRn"
        } else {
            return "M2lyMmRlcDhuMGNrcXJlMGh0ZTIwYTRkcHI6MXY2djZqa3BzdGE4MHQ3Z2k0djFlZmNhb2lrb2hhNmhydmN1dWJsMHQ1b29sdjQxa2E0Yg=="
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }

    // Setup http client for api connection
    private fun getNewHttpClientForAccessToken(sessions: Sessions): OkHttpClient {

        val client = OkHttpClient.Builder()
        client.followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())

        client.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + getSecret(sessions))
                .method(original.method(), original.body())
            Log.d("getSecret", getSecret(sessions))
            val request = requestBuilder.build()
            val mainResponse = chain.proceed(request)
            mainResponse
        }

        return client.build()
    }

//    private fun getNewHttpClientForAccessTokenMobileReload(): OkHttpClient {
//
//        val client = OkHttpClient.Builder()
//        client.followRedirects(true)
//            .followSslRedirects(true)
//            .retryOnConnectionFailure(true)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(httpLoggingInterceptor())
//
//        client.addInterceptor { chain ->
//            val original = chain.request()
//            val requestBuilder = original.newBuilder()
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Authorization", "Basic " + BuildConfig.MOBILE_RELOAD_OAUTH_KEY)
//                .method(original.method(), original.body())
//            val request = requestBuilder.build()
//            val mainResponse = chain.proceed(request)
//            mainResponse
//        }
//
//        return client.build()
//    }
}
