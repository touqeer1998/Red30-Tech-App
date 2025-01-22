package com.example.red30.compose.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun rememberColumns(windowSizeClass: WindowSizeClass) = remember(windowSizeClass) {
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> GridCells.Fixed(1)
        WindowWidthSizeClass.MEDIUM -> GridCells.Fixed(2)
        else -> GridCells.Adaptive(300.dp)
    }
}

val WindowSizeClass.isCompact: Boolean
    get() = windowWidthSizeClass == WindowWidthSizeClass.COMPACT

enum class NavigationType {
    BOTTOM_NAVIGATION,
    RAIL;

    companion object {
        @Composable
        fun rememberNavigationType(
            windowSizeClass: WindowWidthSizeClass
        ): NavigationType = remember(windowSizeClass) {
            when (windowSizeClass) {
                WindowWidthSizeClass.COMPACT -> BOTTOM_NAVIGATION
                else -> RAIL
            }
        }
    }
}
