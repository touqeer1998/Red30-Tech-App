package com.example.red30

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.getSelectedSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConferenceDataUiState>(ConferenceDataUiState.Loading)
    val uiState: StateFlow<ConferenceDataUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                savedStateHandle.getStateFlow<Day>("day", initialValue = Day.Day1)
                    .collect { day ->
                        _uiState.value = ConferenceDataUiState.Loaded(
                            sessionInfos = conferenceRepository.loadConferenceInfo(),
                            day = day
                        )
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                // TODO: handle the empty/error state better
                _uiState.value = ConferenceDataUiState.Loaded()
            }
        }
    }

    fun setDay(day: Day) {
        savedStateHandle["day"] = day

        (_uiState.value as? ConferenceDataUiState.Loaded)?.let {
            _uiState.value = it.copy(day = day)
        }
    }

    fun getSessionInfoById(sessionId: Int) {
        viewModelScope.launch {
            (_uiState.value as? ConferenceDataUiState.Loaded)?.let {
                _uiState.value = it.copy(
                    selectedSession = it.getSelectedSession(sessionId)
                )
            }
        }
    }

    fun toggleFavorite(sessionId: Int) {
        viewModelScope.launch {
            try {
                val favoriteIds = conferenceRepository.toggleFavorite(sessionId)

                (_uiState.value as? ConferenceDataUiState.Loaded)?.let {
                    val updatedSessionInfos = it.sessionInfos.map {
                        it.copy(isFavorite = favoriteIds.contains(it.session.id))
                    }
                    _uiState.value = it.copy(sessionInfos = updatedSessionInfos)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }
}
