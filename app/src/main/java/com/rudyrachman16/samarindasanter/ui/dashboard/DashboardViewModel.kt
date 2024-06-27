package com.rudyrachman16.samarindasanter.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.samarindasanter.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(repository: Repository) : ViewModel() {
    val news = repository.getNews().asLiveData()
}