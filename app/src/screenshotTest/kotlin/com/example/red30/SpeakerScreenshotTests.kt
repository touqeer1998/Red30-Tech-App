package com.example.red30

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.screen.SpeakerItem
import com.example.red30.compose.ui.screen.SpeakersScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Speaker
import com.example.red30.data.fake
import com.example.red30.data.fakes

@Suppress("unused")
class SpeakerScreenshotTests {

    @Preview(showBackground = true)
    @Composable
    fun SpeakersScreenPreview() {
        Red30TechTheme {
            SpeakersScreen(
                uiState = ConferenceDataUiState.fakes()
            )
        }
    }

    @PreviewLightDark
    @Composable
    fun SpeakerItemPreview() {
        Red30TechTheme {
            SpeakerItem(
                speaker = Speaker.fake()
            )
        }
    }
}
