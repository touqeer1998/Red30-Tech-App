package com.example.red30

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

private const val TAG = "MainViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {
    val sessionInfos: StateFlow<List<SessionInfo>> =
        savedStateHandle.getStateFlow<Day>("day", initialValue = Day.Day1)
            .flatMapLatest { day ->
                conferenceRepository.getSessionInfosByDay(day)
            }
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

    val favorites: StateFlow<List<SessionInfo>> = conferenceRepository.favoriteSessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun setDay(day: Day) {
        savedStateHandle["day"] = day
    }
}

class MainViewModelFactory(
    private val conferenceRepository: ConferenceRepository
) : AbstractSavedStateViewModelFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(handle, conferenceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
