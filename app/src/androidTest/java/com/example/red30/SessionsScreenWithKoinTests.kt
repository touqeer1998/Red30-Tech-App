package com.example.red30

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.data.Day
import com.example.red30.data.sessionInfosByDay
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RunWith(AndroidJUnit4::class)
class SessionsScreenWithKoinTests {

    @get:Rule(order = 0)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 1)
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule()

    private lateinit var viewModel: MainViewModel

    @Test
    fun app_launches() {
        composeRule.apply {
            onNodeWithString(R.string.day_1_label).assertExists()
            onNodeWithString(R.string.day_2_label).assertExists()
        }
    }

    @Test
    fun day1_sessions_are_displayed_on_launch() {
        viewModel = composeRule.activity.getViewModel()

        composeRule.apply {
            onAllNodes(testTagStartsWith("ui:sessionItem"))
                .fetchSemanticsNodes()
                .isNotEmpty()

            val uiState = viewModel.uiState.value
            uiState.sessionInfosByDay.slice(0..2).forEach { sessionInfo ->
                onNodeWithText(sessionInfo.session.name).assertIsDisplayed()
                assertThat(sessionInfo.day).isEqualTo(Day.Day1)
            }

            uiState.sessionInfosByDay.slice(2..3).forEach { sessionInfo ->
                onNodeWithText(sessionInfo.session.name).assertExists()
                assertThat(sessionInfo.day).isEqualTo(Day.Day1)
            }
        }
    }

    @Test
    fun speaker_image_is_initial_when_no_image_url() {
        viewModel = composeRule.activity.getViewModel()

        composeRule.apply {
            onAllNodes(testTagStartsWith("ui:sessionItem"))
                .fetchSemanticsNodes()
                .isNotEmpty()

            val uiState = viewModel.uiState.value
            uiState.sessionInfosByDay.slice(0..2).forEach { sessionInfo ->
                with (sessionInfo.speaker) {
                    if (imageUrl.isNullOrEmpty()) {
                        onNodeWithText(name.first().toString()).assertIsDisplayed()
                    }
                }
            }
        }
    }

    @Test
    fun list_updates_to_grid_when_landscape() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        composeRule.apply {
            val firstSessionNode = onAllNodes(
                testTagStartsWith("ui:sessionItem")
            ).onFirst()
            val topBounds = firstSessionNode.getBoundsInRoot().top

            // should be at least one other session on the same row
            firstSessionNode.onSiblings()
                .filter(testTagStartsWith("ui:sessionItem"))
                .onFirst()
                .assertTopPositionInRootIsEqualTo(topBounds)
        }
    }

    @Test
    fun should_scroll_to_top_when_day_is_changed() {
        viewModel = composeRule.activity.getViewModel()

        composeRule.apply {
            var uiState = viewModel.uiState.value

            onNodeWithTag("ui:sessionsList")
                .performScrollToNode(
                    hasText(uiState.sessionInfosByDay.last().session.name)
                )
                .assertIsDisplayed()

            onNodeWithString(R.string.sessions_label)
                .performClick().assertIsSelected()

            onNodeWithText(uiState.sessionInfosByDay.first().session.name)
                .assertIsDisplayed()
        }
    }

    @Test
    fun day2_sessions_are_displayed_when_selected() {
        viewModel = composeRule.activity.getViewModel()

        composeRule.apply {
            onNodeWithString(R.string.day_2_label).performClick().assertIsSelected()

            val uiState = viewModel.uiState.value
            assertThat(uiState.day).isEqualTo(Day.Day2)

            uiState.sessionInfosByDay.slice(0..2).forEach { sessionInfo ->
                onNodeWithText(sessionInfo.session.name).assertIsDisplayed()
                assertThat(sessionInfo.day).isEqualTo(Day.Day2)
            }
        }
    }

    @Test
    fun should_allow_session_to_be_favorited() {
        composeRule.apply {
            onAllNodes(testTagStartsWith("ui:sessionItem"))
                .onFirst()
                .onChildren()
                .filterToOne(hasClickAction())
                .performClick()
                .assert(isOn() and hasContentDescription("un-favorite session"))
        }
    }

    @Test
    fun app_navigates_to_speakers_screen() {
        viewModel = composeRule.activity.getViewModel()

        composeRule.apply {
            onNodeWithString(R.string.speakers_label).performClick()

            val uiState = viewModel.uiState.value
            val speaker = uiState.sessionInfos.first().speaker
            onNodeWithText(speaker.name).assertIsDisplayed()
            onNodeWithText(speaker.organization).assertIsDisplayed()
        }
    }
}
