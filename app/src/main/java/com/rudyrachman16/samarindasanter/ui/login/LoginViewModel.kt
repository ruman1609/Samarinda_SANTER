package com.rudyrachman16.samarindasanter.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudyrachman16.samarindasanter.core.Repository
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var isLogin = mutableStateOf(true)

    private val _loginStatus: MutableState<Status<List<User>>?> = mutableStateOf(null)
    val loginStatus: State<Status<List<User>>?> = _loginStatus
    fun setLoginStatusNull() {
        _loginStatus.value = null
    }

    fun login() {
        viewModelScope.launch {
            repository.login(username.value, password.value).collectLatest {
                _loginStatus.value = it
            }
        }
    }

    private val _registerStatus: MutableState<Status<String>?> = mutableStateOf(null)
    val registerStatus: State<Status<String>?> = _registerStatus
    fun setRegisterStatusToNull() {
        _registerStatus.value = null
    }

    fun register() {
        viewModelScope.launch {
            repository.register(username.value, password.value).collectLatest {
                _registerStatus.value = it
            }
        }
    }
}