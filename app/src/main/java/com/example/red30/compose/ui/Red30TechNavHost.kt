package com.example.red30.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.red30.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Red30TechNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = koinViewModel<MainViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Sessions.route,
        modifier = modifier
    ) {
        composable(route = Screen.Sessions.route) {
            SessionsScreen(
                uiState = uiState,
                onSessionClick = { sessionId ->
                    navController.onSessionClick(viewModel, sessionId)
                },
                onDayClick = viewModel::setDay,
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(uiState = uiState)
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                uiState = uiState,
                onSessionClick = { sessionId ->
                    navController.onSessionClick(viewModel, sessionId)
                },
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
        composable(route = Screen.SessionDetail.route) {
            SessionDetailScreen(uiState = uiState)
        }
    }
}

fun NavController.onSessionClick(viewModel: MainViewModel, sessionId: Int) {
    viewModel.getSessionInfoById(sessionId)
    navigate(Screen.SessionDetail.route)
}
