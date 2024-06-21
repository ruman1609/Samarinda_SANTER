package com.rudyrachman16.samarindasanter.core

import android.content.Context
import androidx.room.Room
import com.rudyrachman16.samarindasanter.core.api.ApiService
import com.rudyrachman16.samarindasanter.core.db.SanterDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {

    private val loggingInterceptor =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
        callTimeout(16, TimeUnit.SECONDS)
        readTimeout(16, TimeUnit.SECONDS)
        connectTimeout(16, TimeUnit.SECONDS)
    }.build()

    private val apiService: ApiService by lazy {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(BuildConfig.BASE_URL)
            client(client)
        }.build().create(ApiService::class.java)
    }

    private var database: SanterDatabase? = null
    private fun getDatabase(context: Context) = database ?: synchronized(this) {
        database ?: Room.databaseBuilder(context, SanterDatabase::class.java, "SanterDatabase")
            .fallbackToDestructiveMigration().build().apply { database = this }
    }

    fun injectRepository(context: Context) = Repository.getInstance(
        apiService, getDatabase(context)
    )
}