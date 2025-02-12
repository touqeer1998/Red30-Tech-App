package com.example.red30.compose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val conferenceRepository: ConferenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConferenceDataUiState>(
        ConferenceDataUiState(isLoading = true)
    )
    val uiState: StateFlow<ConferenceDataUiState> = _uiState

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
}
