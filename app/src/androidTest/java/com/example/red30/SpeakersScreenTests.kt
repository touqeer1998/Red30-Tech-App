package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.test.espresso.device.DeviceInteraction.Companion.setDisplaySize
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.filter.RequiresDisplay
import androidx.test.espresso.device.rules.DisplaySizeRule
import androidx.test.espresso.device.sizeclass.HeightSizeClass
import androidx.test.espresso.device.sizeclass.HeightSizeClass.Companion.HeightSizeClassEnum
import androidx.test.espresso.device.sizeclass.WidthSizeClass
import androidx.test.espresso.device.sizeclass.WidthSizeClass.Companion.WidthSizeClassEnum
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

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule(order = 2)
    var displaySizeRule: DisplaySizeRule = DisplaySizeRule()

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
    fun should_display_portrait_item_when_compact() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.COMPACT,
            heightSizeClass = HeightSizeClass.MEDIUM
        )
        composeRule.setContent {
            Red30TechTheme {
                SpeakersScreen(
                    uiState = ConferenceDataUiState.fakes(),
                )
            }
        }

        composeRule
            .onAllNodesWithTag("ui:portrait-speakerItem").assertCountEquals(2)
    }

    @Test
    fun should_display_portrait_item_when_medium() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.MEDIUM,
            heightSizeClass = HeightSizeClass.EXPANDED
        )
        composeRule.setContent {
            Red30TechTheme {
                SpeakersScreen(
                    uiState = ConferenceDataUiState.fakes()
                )
            }
        }

        try {
            composeRule
                .onAllNodesWithTag("ui:portrait-speakerItem").assertCountEquals(3)
        } catch (e: AssertionError) {
            println(composeRule.onRoot().printToString())
            throw e
        }
    }

    @RequiresDisplay(
        widthSizeClass = WidthSizeClassEnum.EXPANDED,
        heightSizeClass = HeightSizeClassEnum.EXPANDED
    )
    @Test
    fun should_display_portrait_item_when_expanded() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.EXPANDED,
            heightSizeClass = HeightSizeClass.EXPANDED
        )
        composeRule.setContent {
            Red30TechTheme {
                SpeakersScreen(
                    uiState = ConferenceDataUiState.fakes()
                )
            }
        }

        composeRule
            .onAllNodesWithTag("ui:portrait-speakerItem").assertCountEquals(3)
    }
}
