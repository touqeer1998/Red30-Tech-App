package com.example.red30.compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.red30.MainViewModel

private const val SESSION_ID = "sessionId"

@Composable
fun Red30TechNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val sessions by viewModel.sessionInfos.collectAsStateWithLifecycle()
    val speakers by viewModel.speakers.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val selectedSession by viewModel.selectedSession.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Sessions.route,
        modifier = modifier
    ) {
        composable(route = Screen.Sessions.route) {
            SessionsScreen(
                sessions = sessions,
                onSessionClick = { sessionId -> navController.onSessionClick(sessionId) },
                onDayClick = viewModel::setDay,
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(speakers = speakers)
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                sessions = favorites,
                onSessionClick = { sessionId -> navController.onSessionClick(sessionId) },
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
        composable(
            route = Screen.SessionDetail.route,
            arguments = listOf(navArgument(SESSION_ID) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val sessionId = navBackStackEntry.arguments?.getInt(SESSION_ID)
            sessionId?.let {
                viewModel.getSessionInfoById(it)
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "Session detail screen: ${selectedSession.toString()}"
                )
            }
        }
    }
}

fun NavController.onSessionClick(sessionId: Int) {
    navigate(
        Screen.SessionDetail.route.replace("{$SESSION_ID}", sessionId.toString())
    )
}
