package com.rudyrachman16.samarindasanter.ui.dashboard

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.SamarindaSanterTheme
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.databinding.ActivityDashboardBinding
import com.rudyrachman16.samarindasanter.ui.dashboard.route.AssertNewsParcelable
import com.rudyrachman16.samarindasanter.ui.dashboard.route.DashboardRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamarindaSanterTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DashboardRoute.DASHBOARD_ROUTE
                ) {
                    composable(DashboardRoute.DASHBOARD_ROUTE) {
                        DashboardScreen {
                            navController.navigate(DashboardRoute.navigateToDetail(news = it))
                        }
                    }
                    composable(
                        DashboardRoute.DETAIL_ROUTE,
                        arguments = listOf(navArgument(DashboardRoute.NEWS_KEYS) {
                            type = AssertNewsParcelable()
                        })
                    ) {
                        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            it.arguments?.getParcelable(DashboardRoute.NEWS_KEYS, News::class.java)
                        } else {
                            it.arguments?.getParcelable(DashboardRoute.NEWS_KEYS)
                        }
                        if (data == null) {
                            navController.navigateUp()
                            return@composable
                        }
                        DetailScreen(news = data)
                    }
                }
            }
        }
    }
}