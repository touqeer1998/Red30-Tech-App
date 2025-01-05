package com.example.red30

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private val _selectedSession = MutableStateFlow<SessionInfo?>(null)
    val selectedSession: StateFlow<SessionInfo?> = _selectedSession

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

    fun getSessionInfoById(sessionId: Int) {
        viewModelScope.launch {
            _selectedSession.value = conferenceRepository.getSessionInfoById(sessionId)
        }
    }

    fun toggleFavorite(sessionId: Int) {
        viewModelScope.launch {
            val sessionInfo = conferenceRepository.toggleFavorite(sessionId)
            // TODO: use a UiState and update the list of sessionInfos

        }
    }
}
