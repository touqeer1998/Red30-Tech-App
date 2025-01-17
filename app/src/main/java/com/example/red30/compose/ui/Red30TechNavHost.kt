package com.example.red30.compose.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<MainViewModel>(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity
    )
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                onAction = viewModel::onMainAction,
                windowSizeClass = windowSizeClass
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(
                uiState = uiState,
                windowSizeClass = windowSizeClass
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                uiState = uiState,
                onAction = viewModel::onMainAction,
                windowSizeClass = windowSizeClass,
            )
        }
        composable(route = Screen.SessionDetail.route) {
            SessionDetailScreen(
                uiState = uiState,
                windowSizeClass = windowSizeClass
            )
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

    LaunchedEffect(uiState.selectedSession) {
        uiState.selectedSession?.let {
            navController.navigate(Screen.SessionDetail.route)
        }
    }
}
