package com.ccpp.shared.network

import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiService
@Inject constructor(
    private val sessions: Sessions,
    loggingInterceptor: HttpLoggingInterceptor
) {

    // Get Certificate pin by using https://www.ssllabs.com/ssltest/
/*
    private val networkBigWalletApi = Retrofit.Builder()
        .baseUrl(BuildConfig.GIP_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(bigWalletOkHttpClient(authorizationInterceptor, loggingInterceptor))
        .build().create(ApiClient::class.java)

    private fun bigWalletOkHttpClient(authorizationInterceptor: AuthorizationInterceptor, loggingInterceptor: HttpLoggingInterceptor) :OkHttpClient  {
        val client = OkHttpClient.Builder()
        client.followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(loggingInterceptor)

        if (BuildConfig.FLAVOR == "prod") {
            client.certificatePinner(
                CertificatePinner.Builder()
                    .add(
                        "*.2c2p.com",
                        "sha256/S8RbA1Iuoqx3MXyPdyda1VtJsKaF5PWRN10zF30d/pk="
                    )
                    .build()
            )
        }

        return client.build();
    }

    private val networkBigWalletApiMobileReload = Retrofit.Builder()
        .baseUrl(BuildConfig.MOBILE_RELOAD_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(mobileReloadOkHttpClient(authorizationInterceptorMobileReload, loggingInterceptor))
        .build().create(ApiClient::class.java)

    private fun mobileReloadOkHttpClient(authorizationInterceptor: AuthorizationInterceptorMobileReload, loggingInterceptor: HttpLoggingInterceptor) :OkHttpClient  {
        val client = OkHttpClient.Builder()
        client.followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(loggingInterceptor)

        if (BuildConfig.FLAVOR == "prod") {
            client.certificatePinner(
                CertificatePinner.Builder()
                    .add(
                        "*.2c2p.com",
                        "sha256/S8RbA1Iuoqx3MXyPdyda1VtJsKaF5PWRN10zF30d/pk="
                    )
                    .build()
            )
        }

        return client.build();
    }*/

    // fun callSendOTP(data: GenerateOTPReq) = networkBigWalletApi.sendOTP(sessions.getToken(), data)
    // fun verifyOtp(req: VerifyOTPReq) = networkBigWalletApi.verifyOtp(req)
    // fun register(req: BigWalletNewRegisterReq) = networkBigWalletApiMobileReload.register(req)

}
