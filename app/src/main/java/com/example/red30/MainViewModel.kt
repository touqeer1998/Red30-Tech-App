package com.example.red30

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.compose.ui.screen.Screen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.getSelectedSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConferenceDataUiState>(
        ConferenceDataUiState(isLoading = true)
    )
    val uiState: StateFlow<ConferenceDataUiState> = _uiState

    val selectedSession = savedStateHandle.getStateFlow<Int?>(
        Screen.SessionDetail.SESSION_ID, null
    ).map { sessionId ->
        sessionId?.let { _uiState.value.getSelectedSession(sessionId) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        viewModelScope.launch {
            try {
                savedStateHandle.getStateFlow<Day>("day", initialValue = Day.Day1)
                    .collect { day ->
                        _uiState.update {
                            it.copy(
                                sessionInfos = conferenceRepository.loadConferenceInfo(),
                                isLoading = false,
                                day = day
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = R.string.unable_to_load_conference_data_error
                    )
                }
            }
        }
    }

    fun setDay(day: Day) {
        savedStateHandle["day"] = day
        _uiState.update { it.copy(day = day) }
    }

    fun setSelectedSessionId(sessionId: Int) {
        savedStateHandle["sessionId"] = sessionId
    }

    fun toggleFavorite(sessionId: Int) {
        viewModelScope.launch {
            try {
                val favoriteIds = conferenceRepository.toggleFavorite(sessionId)

                _uiState.update {
                    val updatedSessionInfos = it.sessionInfos.map {
                        it.copy(isFavorite = favoriteIds.contains(it.session.id))
                    }
                    it.copy(
                        sessionInfos = updatedSessionInfos,
                        snackbarMessage = R.string.save_remove_favorites_success
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                _uiState.update {
                    it.copy(
                        snackbarMessage = R.string.save_remove_favorites_error,
                    )
                }
            }
        }
    }

    fun activeDestinationClick() = _uiState.update {
        it.copy(shouldAnimateScrollToTop = true)
    }

    fun onScrollComplete() = _uiState.update {
        it.copy(shouldAnimateScrollToTop = false)
    }

    fun shownSnackbar() = _uiState.update {
        it.copy(snackbarMessage = null)
    }
}
