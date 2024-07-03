package com.rudyrachman16.samarindasanter.ui.dashboard.route

import android.net.Uri
import com.google.gson.Gson
import com.rudyrachman16.samarindasanter.core.model.News

object DashboardRoute {
    const val DASHBOARD_ROUTE = "dashboard"

    const val NEWS_KEYS = "news"
    const val DETAIL_ROUTE = "dashboard/{$NEWS_KEYS}"

    @JvmStatic
    fun navigateToDetail(news: News): String {
        val route = "dashboard/"
        val json = Uri.encode(Gson().toJson(news))
        return route + json
    }
}