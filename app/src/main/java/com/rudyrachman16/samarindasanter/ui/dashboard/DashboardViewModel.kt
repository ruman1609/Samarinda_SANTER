package com.rudyrachman16.samarindasanter.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.samarindasanter.core.Repository

class DashboardViewModel(repository: Repository): ViewModel() {
    val news = repository.getNews().asLiveData()
}