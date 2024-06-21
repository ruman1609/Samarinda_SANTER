package com.rudyrachman16.samarindasanter

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rudyrachman16.samarindasanter.core.Injection
import com.rudyrachman16.samarindasanter.ui.dashboard.DashboardViewModel
import com.rudyrachman16.samarindasanter.ui.login.MainViewModel

class ViewModelFactory private constructor(private val applicationContext: Context) :
    AbstractSavedStateViewModelFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(applicationContext: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelFactory(applicationContext).apply { INSTANCE = this }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        val repo = Injection.injectRepository(applicationContext)
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repo) as T

            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repo) as T

            else -> throw (ClassNotFoundException("Unknown ViewModel Class"))
        }
    }
}