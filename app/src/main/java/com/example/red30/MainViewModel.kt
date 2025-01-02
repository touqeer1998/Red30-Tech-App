package com.example.red30

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Session
import com.example.red30.data.Speaker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.jvm.java

class MainViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {
    val sessions: StateFlow<List<Session>> = conferenceRepository.sessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val speakers: StateFlow<List<Speaker>> = conferenceRepository.speakers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
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
