package com.example.red30.compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

@Composable
fun Red30TechContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    currentDestination: NavDestination? = null,
    showNavigationRail: Boolean = false,
    onActiveDestinationClick: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxSize()) {
        if (showNavigationRail) {
            Red30TechNavigationRail(
                navController = navController,
                currentDestination = currentDestination,
                onActiveDestinationClick = onActiveDestinationClick
            )
        }

        Red30TechNavHost(
            navController = navController,
            snackbarHostState = snackbarHostState,
            modifier = modifier.padding(paddingValues)
        )
    }
}
