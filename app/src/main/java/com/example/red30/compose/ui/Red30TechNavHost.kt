package com.example.red30.compose.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.red30.MainViewModel

private const val TAG = "Red30TechNavHost"

@Composable
fun Red30TechNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val sessions by viewModel.sessions.collectAsStateWithLifecycle()
    val speakers by viewModel.speakers.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Sessions.route,
        modifier = modifier
    ) {
        composable(route = Screen.Sessions.route) {
            SessionsScreen(
                sessions = sessions,
                onSessionClick = {
                    navController.navigate(Screen.SessionDetail.route)
                }
            )
        }
        composable(route = Screen.Speakers.route) {
            SpeakersScreen(
                speakers = speakers,
                onSpeakerClick = {
                    navController.navigate(Screen.SpeakerDetail.route)
                }
            )
        }
        composable(route = Screen.Favorites.route) {
            Text("Favorites screen")
        }
        composable(route = Screen.SessionDetail.route) {
            Text("Session detail screen")
        }
        composable(route = Screen.SpeakerDetail.route) {
            Text("Speaker detail screen")
        }
    }
}
