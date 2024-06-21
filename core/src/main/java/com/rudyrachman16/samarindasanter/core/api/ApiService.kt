package com.rudyrachman16.samarindasanter.core.api

import com.google.gson.JsonObject
import com.rudyrachman16.samarindasanter.core.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET("berita_ppid")
    @Headers(
        "Authorization: Bearer ${BuildConfig.TOKEN}",
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getNews(): JsonObject
}