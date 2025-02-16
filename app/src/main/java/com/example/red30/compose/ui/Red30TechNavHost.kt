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
import com.example.red30.compose.ui.screen.Screen
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
        startDestination = Screen.Sessions.route
    ) {
        composable(route = Screen.Sessions.route) {
            SessionsScreen(uiState = uiState)
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(uiState = uiState)
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(uiState = uiState)
        }
        composable(route = Screen.SessionDetail.route) {
            uiState.selectedSession?.let { sessionInfo ->
                SessionDetailScreen(sessionInfo = sessionInfo)
            }
        }
    }
}
