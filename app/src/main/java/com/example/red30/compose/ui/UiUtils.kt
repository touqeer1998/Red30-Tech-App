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

enum class NavigationType {
    BOTTOM_NAVIGATION,
    RAIL;

    companion object {
        fun forWindowSizeSize(
            windowSizeClass: WindowWidthSizeClass
        ): NavigationType = when (windowSizeClass) {
            WindowWidthSizeClass.COMPACT -> BOTTOM_NAVIGATION
            else -> RAIL
        }
    }
}
