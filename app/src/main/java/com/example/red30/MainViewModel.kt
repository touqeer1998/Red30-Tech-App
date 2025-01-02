package com.example.red30

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {
    val sessionInfos: StateFlow<List<SessionInfo>> = conferenceRepository.getSessionInfosByDay()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val speakers: StateFlow<List<Speaker>> = conferenceRepository.speakers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            Log.d(TAG, "loading the conference info")
            conferenceRepository.loadConferenceInfo()
        }
    }
}

class MainViewModelFactory(
    private val conferenceRepository: ConferenceRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(conferenceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
