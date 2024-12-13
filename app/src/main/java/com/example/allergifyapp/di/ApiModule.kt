package com.example.allergifyapp.di

import com.example.allergifyapp.data.remote.api.ApiService
import com.example.allergifyapp.data.remote.api.ApiService2
import com.example.allergifyapp.data.remote.api.ApiServiceGemini
import com.example.allergifyapp.localdata.PreferencesManager
import com.example.allergifyapp.utils.Constant.BASE_URL
import com.example.allergifyapp.utils.Constant.BASE_URL2
import com.example.allergifyapp.utils.Constant.BASE_URL_GEMINI
import com.example.allergifyapp.utils.Constant.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun provideBaseUrl2() = BASE_URL2

    @Provides
    fun provideBaseUrlGemini() = BASE_URL_GEMINI

    @Provides
    fun provideNetworkTimeout() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(preferencesManager: PreferencesManager): Interceptor {
        return Interceptor { chain ->
            val token = preferencesManager.getToken()
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
        timeout: Long
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofit2")
    fun provideRetrofit2(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl2())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofitGemini")
    fun provideRetrofitGemini(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrlGemini())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("retrofit")retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService2(@Named("retrofit2")retrofit: Retrofit): ApiService2 {
        return retrofit.create(ApiService2::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceGemini(@Named("retrofitGemini")retrofit: Retrofit): ApiServiceGemini {
        return retrofit.create(ApiServiceGemini::class.java)
    }
}