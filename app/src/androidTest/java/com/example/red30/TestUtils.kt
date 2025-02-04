package com.example.red30

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithString(
    @StringRes resourceId: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(resourceId))

fun testTagStartsWith(prefix: String) = SemanticsMatcher(
    "Contains substring in TestTag: $prefix"
) { node ->
    val testTag = node.config.getOrNull(SemanticsProperties.TestTag)
    testTag?.startsWith(prefix) == true
}
