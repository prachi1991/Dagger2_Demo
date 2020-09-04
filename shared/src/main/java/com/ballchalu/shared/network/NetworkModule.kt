package com.ballchalu.shared.network

import com.ballchalu.shared.BuildConfig
import com.ballchalu.shared.core.base.BaseRepository
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.network.repository.*
import com.ballchalu.shared.util.ConstantsBase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideInterceptor(
        sharedPref: SharedPreferenceStorage
    ): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .build()
            val request = original.newBuilder()
                .addHeader("Api-Key", BuildConfig.apiKey)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${sharedPref.token.toString()}")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url).build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(ConstantsBase.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(ConstantsBase.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(ConstantsBase.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        preferenceStorage: SharedPreferenceStorage,
        apiService: ApiService,
        baseRepository: BaseRepository
    ): LoginRepository = LoginRepository(
        preferenceStorage, apiService, baseRepository
    )

    @Provides
    @Singleton
    fun provideMatchesListingRepository(
        preferenceStorage: SharedPreferenceStorage,
        apiService: ApiService,
        baseRepository: BaseRepository
    ): MatchesRepository = MatchesRepository(preferenceStorage, apiService, baseRepository)

    @Provides
    @Singleton
    fun provideMatchDetailsRepository(
        preferenceStorage: SharedPreferenceStorage,
        apiService: ApiService,
        baseRepository: BaseRepository
    ): MatchDetailsRepository =
        MatchDetailsRepository(preferenceStorage, apiService, baseRepository)

    @Provides
    @Singleton
    fun provideSplashRepository(
        apiService: ApiService,
        baseRepository: BaseRepository
    ): SplashRepository =
        SplashRepository(
            apiService,
            baseRepository
        )


    @Provides
    @Singleton
    fun provideContestRepository(
        apiService: ApiService,
        baseRepository: BaseRepository
    ): ContestRepository =
        ContestRepository(
            apiService,
            baseRepository
        )

    @Provides
    @Singleton
    fun provideCreateBetRepository(
        apiService: ApiService,
        baseRepository: BaseRepository
    ): CreateBetRepository =
        CreateBetRepository(
            apiService,
            baseRepository
        )

    @Provides
    @Singleton
    fun provideMyBetsRepository(
        apiService: ApiService,
        baseRepository: BaseRepository
    ): MyBetsRepository =
        MyBetsRepository(
            apiService,
            baseRepository
        )

    @Provides
    @Singleton
    fun provideWinnerRepository(
        apiService: ApiService,
        baseRepository: BaseRepository
    ): WinnerRepository =
        WinnerRepository(
            apiService,
            baseRepository
        )


}
