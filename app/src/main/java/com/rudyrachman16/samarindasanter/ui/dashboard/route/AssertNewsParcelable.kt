package com.rudyrachman16.samarindasanter.ui.dashboard.route

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rudyrachman16.samarindasanter.core.model.News

class AssertNewsParcelable : NavType<News>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): News? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, News::class.java)
        } else {
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): News =
        Gson().fromJson(value, TypeToken.get(News::class.java))

    override fun put(bundle: Bundle, key: String, value: News) {
        bundle.putParcelable(key, value)
    }
}