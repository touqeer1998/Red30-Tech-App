package com.example.red30.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.red30.compose.ui.Red30TechBottomBar
import com.example.red30.compose.ui.Red30TechNavHost
import com.example.red30.compose.ui.Red30TechNavigationRail
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import org.koin.androidx.compose.koinViewModel

@Composable
fun Red30TechApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    Red30TechTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val snackbarHostState = remember { SnackbarHostState() }

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val navigationType = windowSizeClass.navigationType

        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    Red30TechBottomBar(
                        navController = navController,
                        currentDestination = currentDestination,
                        onActiveDestinationClick = viewModel::activeDestinationClick
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            Row {
                if (navigationType == NavigationType.RAIL) {
                    Red30TechNavigationRail(
                        navController = navController,
                        currentDestination = currentDestination,
                        onActiveDestinationClick = viewModel::activeDestinationClick
                    )
                }
                Red30TechNavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    viewModel = viewModel
                )
            }
        }
    }
}

enum class NavigationType {
    BOTTOM_NAVIGATION,
    RAIL
}

val WindowSizeClass.navigationType: NavigationType
    get() = when (windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> NavigationType.RAIL
        else -> NavigationType.BOTTOM_NAVIGATION
    }

@Preview(showBackground = true)
@Composable
fun Red30TechAppPreview() {
    val viewModel = MainViewModel(
        savedStateHandle = SavedStateHandle(),
        conferenceRepository = FakeConferenceRepository()
    )
    Red30TechApp(viewModel = viewModel)
}

private class FakeConferenceRepository: ConferenceRepository {
    override suspend fun loadConferenceInfo(): List<SessionInfo> {
        return listOf(
            SessionInfo.fake(),
            SessionInfo.fake2(),
            SessionInfo.fake3(),
            SessionInfo.fake4(),
        )
    }

    override suspend fun toggleFavorite(sessionId: Int): List<Int> {
        TODO("Not yet implemented")
    }
}
