package com.rudyrachman16.samarindasanter

import androidx.lifecycle.ViewModel
import com.rudyrachman16.samarindasanter.core.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun login(username: String, password: String) {
        repository.login(username, password)
    }
}