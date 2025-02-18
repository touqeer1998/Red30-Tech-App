package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isOn
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.printToString
import androidx.lifecycle.SavedStateHandle
import androidx.test.espresso.device.DeviceInteraction.Companion.setDisplaySize
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.rules.DisplaySizeRule
import androidx.test.espresso.device.sizeclass.HeightSizeClass
import androidx.test.espresso.device.sizeclass.WidthSizeClass
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.MainViewModel
import com.example.red30.compose.Red30TechApp
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import com.example.red30.data.fake5
import com.example.red30.data.fakes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Red30TechAppTests {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule(order = 2)
    var displaySizeRule: DisplaySizeRule = DisplaySizeRule()

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

    @Test
    fun should_allow_session_to_be_favorited() {
        composeRule.setContent {
            Red30TechApp()
        }

        composeRule.apply {
            onAllNodes(hasTestTag("ui:sessionItem"))
                .onFirst()
                .onChildren()
                .filterToOne(hasClickAction())
                .performClick()
                .assert(isOn() and hasContentDescription("un-favorite session"))
        }
    }

    @Test
    fun should_display_bottom_bar_when_compact() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.COMPACT,
            heightSizeClass = HeightSizeClass.MEDIUM
        )

        composeRule.setContent {
            Red30TechApp()
        }

        composeRule
            .onNodeWithTag("ui:bottomBar").assertIsDisplayed()
    }

    @Test
    fun should_display_rail_when_expanded() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.EXPANDED,
            heightSizeClass = HeightSizeClass.MEDIUM
        )

        composeRule.setContent {
            Red30TechApp()
        }

        composeRule
            .onNodeWithTag("ui:navigationRail").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_handle_config_change_restoration_on_sessions_screen() {
        val stateRestorationTester = StateRestorationTester(composeRule)
        stateRestorationTester.setContent {
            Red30TechApp()
        }

        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.day_2_label))
            .assertIsNotSelected()
            .performClick()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.day_2_label))
            .assertIsSelected()
    }

    @Test
    fun should_scroll_to_top_when_tab_clicked() {
        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(),
            conferenceRepository = FakeConferenceRepository()
        )
        composeRule.setContent {
            Red30TechApp(viewModel = viewModel)
        }

        composeRule.apply {
            onNodeWithTag("ui:sessionsList")
                .performScrollToNode(
                    hasText(SessionInfo.fake5().session.name)
                )
                .assertIsDisplayed()

            onNodeWithText(activity.getString(R.string.sessions_label))
                .performClick()
                .assertIsSelected()

            onNodeWithText(SessionInfo.fake().session.name)
                .assertIsDisplayed()
        }
    }
}

private class FakeConferenceRepository: ConferenceRepository {
    override suspend fun loadConferenceInfo(): List<SessionInfo> {
        return listOf(
            SessionInfo.fake(),
            SessionInfo.fake2(),
            SessionInfo.fake3(),
            SessionInfo.fake4(),
            SessionInfo.fake5(),
        )
    }

    override suspend fun toggleFavorite(sessionId: Int): List<Int> {
        TODO("Not yet implemented")
    }
}