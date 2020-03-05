package com.ccpp.shared.network

import com.ccpp.shared.BuildConfig
import com.ccpp.shared.core.base.BaseRepository
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.network.repository.LoginRepository
import com.ccpp.shared.network.repository.MatchesRepository
import com.ccpp.shared.network.repository.MatchDetailsRepository
import com.ccpp.shared.network.repository.SplashRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSessions(): Sessions {
        return Sessions()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: com.ccpp.shared.network.Interceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()

        client.followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)

        return client.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }

    /*@Provides
    @Singleton
    fun provideAuthorizationInterceptor(sessions: Sessions): Interceptor {
        return AuthorizationInterceptor(sessions)
    }*/

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        preferenceStorage: SharedPreferenceStorage,
        apiService: ApiService,
        baseRepository: BaseRepository
    ): LoginRepository = LoginRepository(
        preferenceStorage, apiService, baseRepository)

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



}
