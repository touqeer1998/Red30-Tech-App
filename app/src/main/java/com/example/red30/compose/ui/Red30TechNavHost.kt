package com.example.red30.compose.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.red30.MainViewModel
import com.example.red30.compose.ui.screen.FavoritesScreen
import com.example.red30.compose.ui.screen.Screen
import com.example.red30.compose.ui.screen.SessionDetailScreen
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.compose.ui.screen.SpeakersScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Red30TechNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    shouldAnimateScrollToTop: Boolean = false
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
                shouldAnimateScrollToTop = shouldAnimateScrollToTop,
                onSessionClick = { sessionId ->
                    navController.onSessionClick(viewModel, sessionId)
                },
                onDayClick = viewModel::setDay,
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(
                uiState = uiState,
                shouldAnimateScrollToTop = shouldAnimateScrollToTop
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                uiState = uiState,
                shouldAnimateScrollToTop = shouldAnimateScrollToTop,
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

    uiState.snackbarMessage?.let { snackbarMessage ->
        val message = stringResource(snackbarMessage)
        LaunchedEffect(snackbarHostState, viewModel, snackbarMessage, message) {
            snackbarHostState.showSnackbar(message)
            viewModel.shownSnackbar()
        }
    }
}

fun NavController.onSessionClick(viewModel: MainViewModel, sessionId: Int) {
    viewModel.getSessionInfoById(sessionId)
    navigate(Screen.SessionDetail.route)
}
