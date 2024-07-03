package com.rudyrachman16.samarindasanter.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudyrachman16.samarindasanter.core.Repository
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.core.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var warningMessage = mutableStateOf("")
    var isEmpty = mutableStateOf(false)
    var newsData = mutableStateListOf<News>()

    private val _newsStatus: MutableState<Status<List<News>>?> = mutableStateOf(null)
    val newsStatus: State<Status<List<News>>?> = _newsStatus
    fun setNewsStatusToNull() {
        _newsStatus.value = null
    }

    fun getNews() {
        viewModelScope.launch {
            repository.getNews().collectLatest {
                _newsStatus.value = it
            }
        }
    }
}