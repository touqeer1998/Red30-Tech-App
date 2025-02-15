package com.example.red30.compose

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.getSelectedSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConferenceDataUiState>(
        ConferenceDataUiState(isLoading = true)
    )
    val uiState: StateFlow<ConferenceDataUiState> = _uiState

    val selectedSession = savedStateHandle.getStateFlow<Int?>(
        "sessionId", null
    ).map { sessionId ->
        sessionId?.let { _uiState.value.getSelectedSession(sessionId) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        viewModelScope.launch {
            val sessionsInfos = conferenceRepository.loadConferenceInfo()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    sessionInfos = sessionsInfos
                )
            }
            Log.i(TAG, "initialized: $sessionsInfos")
        }
    }

    fun setSelectedSessionId(sessionId: Int) {
        savedStateHandle["sessionId"] = sessionId
    }
}
