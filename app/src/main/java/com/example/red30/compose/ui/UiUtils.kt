package com.example.red30.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

enum class NavigationType {
    BOTTOM_NAVIGATION,
    RAIL;

    companion object {
        @Composable
        fun rememberNavigationType(
            windowSizeClass: WindowSizeClass
        ) = remember(windowSizeClass) {
            when (windowSizeClass.windowWidthSizeClass) {
                WindowWidthSizeClass.EXPANDED -> RAIL
                else -> BOTTOM_NAVIGATION
            }
        }
    }
}
