package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.fakes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionsScreenTests {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun should_display_empty_view_when_no_data() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        isLoading = false
                    )
                )
            }
        }

        composeRule
            .onNodeWithTag("ui:emptyConferenceData").assertIsDisplayed()
    }

    @Test
    fun should_display_day_1_sessions() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        isLoading = false,
                        sessionInfos = listOf(
                            SessionInfo.fake(),
                            SessionInfo.fake2(),
                            SessionInfo.fake3(),
                        )
                    )
                )
            }
        }

        composeRule.apply {
            onNodeWithString(R.string.day_1_label).assertIsSelected()
            onAllNodesWithTag("ui:sessionItem").assertCountEquals(2)
        }
    }

    @Test
    fun should_display_day_2_as_selected_when_clicked() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState.fakes()
                )
            }
        }

        composeRule.apply {
            onNodeWithString(R.string.day_2_label).performClick().assertIsSelected()
        }
    }
}
