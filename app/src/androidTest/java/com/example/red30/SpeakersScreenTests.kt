package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.ForcedSize
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.screen.SpeakersScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.fakes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class SpeakersScreenTests {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun should_display_empty_view_when_no_data() {
        composeRule.setContent {
            Red30TechTheme {
                SpeakersScreen(
                    uiState = ConferenceDataUiState(
                        isLoading = false,
                        sessionInfos = emptyList()
                    )
                )
            }
        }

        composeRule
            .onNodeWithTag("ui:emptyConferenceData")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_default_item_when_compact() {
        composeRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(480.dp, 720.dp))
            ) {
                Red30TechTheme {
                    SpeakersScreen(
                        uiState = ConferenceDataUiState.fakes(),
                    )
                }
            }
        }

        composeRule
            .onAllNodesWithTag("ui:speakerItem")
            .assertCountEquals(2)
    }

    @Test
    fun should_display_portrait_item_when_medium() {
        composeRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(720.dp, 480.dp))
            ) {
                Red30TechTheme {
                    SpeakersScreen(
                        uiState = ConferenceDataUiState.fakes()
                    )
                }
            }
        }

        composeRule
            .onAllNodesWithTag("ui:portrait-speakerItem")
            .assertCountEquals(2)
    }

    @Test
    fun should_display_portrait_item_when_expanded() {
        composeRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(1280.dp, 800.dp))
            ) {
                Red30TechTheme {
                    SpeakersScreen(
                        uiState = ConferenceDataUiState.fakes()
                    )
                }
            }
        }

        composeRule
            .onAllNodesWithTag("ui:portrait-speakerItem")
            .assertCountEquals(2)
    }
}
