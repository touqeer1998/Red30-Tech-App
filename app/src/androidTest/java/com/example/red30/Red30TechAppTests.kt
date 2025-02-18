package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.Red30TechApp
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.fakes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Red30TechAppTests {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun app_launches() {
        composeRule.setContent {
            Red30TechApp()
        }

        composeRule.apply {
            println(onRoot().printToString())
        }
    }

    @Test
    fun loading_indicator_properly_displayed() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState(isLoading = true)
                )
            }
        }

        composeRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun should_display_empty_view_when_no_data() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState(isLoading = false)
                )
            }
        }

        composeRule.onNodeWithTag("ui:emptyConferenceData").assertIsDisplayed()
    }

    @Test
    fun should_display_day_2_chip_as_selected() {
        composeRule.setContent {
            Red30TechTheme {
                SessionsScreen(
                    uiState = ConferenceDataUiState.fakes()
                )
            }
        }

        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.day_2_label))
            .performClick()
            .assertIsSelected()
    }
}
