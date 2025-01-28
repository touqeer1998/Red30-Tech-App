package com.example.red30

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.Red30TechApp
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.data.fake
import com.example.red30.data.fake2
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
class Red30TechAppTests {

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

    @Test
    fun app_launches() {
        composeRule.setContent {
            Red30TechApp()
        }

        composeRule.apply {
            onNodeWithString(R.string.day_1_label).assertExists()
            onNodeWithString(R.string.day_2_label).assertExists()
        }
    }

    @Test
    fun app_navigates_to_speakers_screen() {
        composeRule.setContent {
            Red30TechApp()
        }

        composeRule.apply {
            onNodeWithString(R.string.speakers_label).performClick()

            onNodeWithText(Speaker.fake().name).assertExists()
            onNodeWithText(Speaker.fake().organization).assertExists()
        }
    }

    @Test
    fun should_handle_config_change_restoration_on_sessions_screen() {
        val stateRestorationTester = StateRestorationTester(composeRule)
        stateRestorationTester.setContent {
            Red30TechApp()
        }

        composeRule.apply {
            onNodeWithString(R.string.day_1_label).assertIsSelected()

            onNodeWithString(R.string.day_2_label).performClick()

            // verify first day 2 session is displayed
            try {
                onNodeWithText(SessionInfo.fake2().session.name).assertIsDisplayed()
            } catch (e: AssertionError) {
                println(composeRule.onRoot().printToString())
                throw e
            }

            stateRestorationTester.emulateSavedInstanceStateRestore()

            onNodeWithString(R.string.day_2_label).assertIsSelected()
            onNodeWithText(SessionInfo.fake2().session.name).assertIsDisplayed()
        }
    }
}
