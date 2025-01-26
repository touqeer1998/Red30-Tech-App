package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.device.rules.DisplaySizeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.data.ConferenceDataUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class SessionsScreenTests {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule(order = 2)
    var displaySizeRule: DisplaySizeRule = DisplaySizeRule()

    @Test
    fun should_display_empty_view_when_no_data() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
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
}
