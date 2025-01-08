package com.example.red30

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.red30.compose.ui.SessionDetailScreen
import com.example.red30.compose.ui.SessionItem
import com.example.red30.compose.ui.SessionsScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.compose.theme.Red30TechTheme

@Suppress("unused")
class SessionScreenshotTests {

    @Preview(showBackground = true)
    @Composable
    fun SessionDetailScreenPreview() {
        Red30TechTheme {
            SessionDetailScreen(
                uiState = ConferenceDataUiState(
                    selectedSession = SessionInfo.fake(),
                )
            )
        }
    }

    @PreviewLightDark
    @Composable
    fun SessionItemPreview() {
        Red30TechTheme {
            SessionItem(
                sessionInfo = SessionInfo.fake()
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SessionScreenLoadingPreview() {
        Red30TechTheme {
            SessionsScreen(
                uiState = ConferenceDataUiState(isLoading = true)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SessionScreenPreview() {
        Red30TechTheme {
            SessionsScreen(
                uiState = ConferenceDataUiState(
                    sessionInfos = listOf(SessionInfo.fake(), SessionInfo.fake2())
                )
            )
        }
    }
}
