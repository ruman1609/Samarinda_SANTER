package com.rudyrachman16.samarindasanter.core

import android.content.Context
import androidx.room.Room
import com.rudyrachman16.samarindasanter.core.api.ApiService
import com.rudyrachman16.samarindasanter.core.db.SanterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Singleton
    @Provides
    fun loggingInterceptor() =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Singleton
    @Provides
    fun client(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
        callTimeout(16, TimeUnit.SECONDS)
        readTimeout(16, TimeUnit.SECONDS)
        connectTimeout(16, TimeUnit.SECONDS)
    }.build()

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService = Retrofit.Builder().apply {
        addConverterFactory(GsonConverterFactory.create())
        baseUrl(BuildConfig.BASE_URL)
        client(client)
    }.build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, SanterDatabase::class.java, "SanterDatabase")
            .fallbackToDestructiveMigration().build()
}