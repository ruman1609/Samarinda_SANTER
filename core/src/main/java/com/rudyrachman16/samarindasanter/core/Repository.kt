package com.rudyrachman16.samarindasanter.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rudyrachman16.samarindasanter.core.api.ApiService
import com.rudyrachman16.samarindasanter.core.db.SanterDatabase
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.core.model.User
import com.rudyrachman16.samarindasanter.core.utils.FlowUtils

class Repository private constructor(
    private val apiService: ApiService,
    private val dbService: SanterDatabase
) {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService, dbService: SanterDatabase) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(apiService, dbService).apply { INSTANCE = this }
            }
    }

    private val userDao get() = dbService.getUserDao()

    fun login(username: String, password: String) = FlowUtils.defaultFlowCallback {
        userDao.login(username, password)
    }

    fun register(username: String, password: String) = FlowUtils.defaultFlowCallback {
        userDao.registration(User(username, password))
        "Berhasil, mendaftarkan $username"
    }

    fun getNews() = FlowUtils.defaultFlowCallback {
        val result = apiService.getNews()
        val data = result.getAsJsonArray("data")
        Gson().fromJson<List<News>>(
            data.toString(),
            TypeToken.getParameterized(List::class.java, News::class.java).type
        )
    }
}