package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.device.DeviceInteraction.Companion.setDisplaySize
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.rules.DisplaySizeRule
import androidx.test.espresso.device.sizeclass.HeightSizeClass
import androidx.test.espresso.device.sizeclass.WidthSizeClass
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.Red30TechApp
import com.example.red30.data.ConferenceRepository
import com.example.red30.fakes.FakeConferenceRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class AppNavigationTests {

    @get:Rule(order = 0)
    val koinRule = KoinTestRule(
        modules = listOf(
            module {
                singleOf(::FakeConferenceRepository) bind ConferenceRepository::class
                viewModelOf(::MainViewModel)
            }
        )
    )

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule(order = 2)
    var displaySizeRule: DisplaySizeRule = DisplaySizeRule()

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
    fun should_display_bottom_bar_when_medium() {
        onDevice().setDisplaySize(
            widthSizeClass = WidthSizeClass.MEDIUM,
            heightSizeClass = HeightSizeClass.MEDIUM
        )

        composeRule.setContent {
            Red30TechApp()
        }

        composeRule
            .onNodeWithTag("ui:bottomBar").assertIsDisplayed()
    }
}
