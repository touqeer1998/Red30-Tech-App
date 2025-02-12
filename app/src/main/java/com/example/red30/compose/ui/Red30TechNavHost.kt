package com.example.red30.compose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.red30.compose.MainViewModel
import com.example.red30.compose.ui.screen.FavoritesScreen
import com.example.red30.compose.ui.screen.SessionDetailScreen
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.compose.ui.screen.SpeakersScreen

@Composable
fun Red30TechNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = "sessions"
    ) {
        composable(route = "sessions") {
            SessionsScreen(
                uiState = uiState,
                onButtonClick = {
                    navController.navigate("speakers")
                }
            )
        }
        composable(route = "speakers") {
            SpeakersScreen(uiState = uiState)
        }
        composable(route = "favorites") {
            FavoritesScreen(uiState = uiState)
        }
        composable(route = "sessionDetails") {
            uiState.selectedSession?.let { sessionInfo ->
                SessionDetailScreen(sessionInfo = sessionInfo)
            }
        }
    }
}
