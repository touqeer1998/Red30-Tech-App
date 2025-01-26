package com.example.red30.compose.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<MainViewModel>(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedSession by viewModel.selectedSession.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Sessions.route,
        modifier = modifier.consumeWindowInsets(
            WindowInsets.systemBars
        )
    ) {
        composable(route = Screen.Sessions.route) {
            SessionsScreen(
                uiState = uiState,
                onDayClick = viewModel::setDay,
                onScrollComplete = viewModel::onScrollComplete,
                onFavoriteClick = viewModel::toggleFavorite,
                navigateToSessionDetail = { sessionId ->
                    viewModel.setSelectedSessionId(sessionId)
                    navController.navigate(
                        Screen.SessionDetail.getDestination(sessionId)
                    )
                }
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(
                uiState = uiState
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                uiState = uiState,
                onScrollComplete = viewModel::onScrollComplete,
                onFavoriteClick = viewModel::toggleFavorite,
                navigateToSessionDetail = { sessionId ->
                    viewModel.setSelectedSessionId(sessionId)
                    navController.navigate(
                        Screen.SessionDetail.getDestination(sessionId)
                    )
                }
            )
        }
        composable(
            route = Screen.SessionDetail.getCompleteRoute(),
            arguments = listOf(
                navArgument(Screen.SessionDetail.SESSION_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            selectedSession?.let { sessionInfo ->
                SessionDetailScreen(
                    sessionInfo = sessionInfo
                )
            }
        }
    }

    uiState.snackbarMessage?.let { snackbarMessage ->
        val message = stringResource(snackbarMessage)
        LaunchedEffect(snackbarHostState, snackbarMessage) {
            val result = snackbarHostState.showSnackbar(message)
            if (result == SnackbarResult.Dismissed) {
                viewModel.shownSnackbar()
            }
        }
    }
}
