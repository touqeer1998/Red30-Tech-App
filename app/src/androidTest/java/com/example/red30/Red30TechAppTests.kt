package com.example.red30

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.red30.compose.Red30TechApp
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Speaker
import com.example.red30.data.fake
import com.example.red30.fakes.FakeConferenceRepository
import org.junit.Before
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

    @Before
    fun setUp() {
        composeRule.setContent {
            Red30TechApp()
        }
    }

    @Test
    fun app_launches() {
        composeRule
            .onNodeWithString(R.string.day_1_label)
            .assertExists()

        composeRule
            .onNodeWithString(R.string.day_2_label)
            .assertExists()
    }

    @Test
    fun app_navigates_to_speakers_screen() {
        composeRule
            .onNodeWithString(R.string.speakers_label)
            .performClick()

        composeRule
            .onNodeWithText(Speaker.fake().name)
            .assertExists()
    }

    @Test
    fun app_navigates_to_speaker_details_screen() {
        composeRule
            .onNodeWithString(R.string.speakers_label)
            .performClick()

        with(Speaker.fake()) {
            composeRule
                .onNodeWithText(name)
                .assertExists()

            composeRule
                .onNodeWithText(name)
                .performClick()

            composeRule
                .onNodeWithText(title)
                .assertExists()
            composeRule
                .onNodeWithText(organization)
                .assertExists()
        }
    }
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithString(
    @StringRes resourceId: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(resourceId))
