package com.rudyrachman16.samarindasanter.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.samarindasanter.core.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun login(username: String, password: String) = repository.login(username, password).asLiveData()

    fun register(username: String, password: String) = repository.register(username, password).asLiveData()
}