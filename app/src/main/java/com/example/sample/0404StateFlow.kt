package com.example.sample

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

internal class MainViewModel2(
    private val conferenceRepository: ConferenceRepository = FakeConferenceRepository2()
): ViewModel() {

    private val _uiState = MutableStateFlow<ConferenceDataUiState>(
        ConferenceDataUiState(isLoading = true)
    )
    val uiState: StateFlow<ConferenceDataUiState> = _uiState

    init {
        viewModelScope.launch {
            val sessionInfos = conferenceRepository.loadConferenceInfo()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    sessionInfos = sessionInfos
                )
            }
            Log.i(TAG, "initialized: $sessionInfos")
        }
    }
}

@Composable
private fun MainApp(modifier: Modifier = Modifier) {
    // This will use the koinViewModel function in practice
    val viewModel = MainViewModel2(conferenceRepository = FakeConferenceRepository2())
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            uiState.sessionInfos.forEach {
                Text(it.session.name)
            }
        }
    }
}

private class FakeConferenceRepository2: ConferenceRepository {
    override suspend fun loadConferenceInfo(): List<SessionInfo> {
        return listOf(SessionInfo.fake(), SessionInfo.fake2())
    }

    override suspend fun toggleFavorite(sessionId: Int): List<Int> {
        TODO("Not yet implemented")
    }
}

@Preview
@Composable
private fun StateFlow0404Preview() {
    Red30TechTheme {
        MainApp()
    }
}
