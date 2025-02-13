package com.example.red30.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.red30.compose.ui.Red30TechBottomBar
import com.example.red30.compose.ui.Red30TechNavHost
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

        Scaffold(
            bottomBar = {
                Red30TechBottomBar()
            }
        ) { innerPadding ->
            Red30TechNavHost(
                modifier = modifier.padding(innerPadding),
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Red30TechAppPreview() {
    val viewModel = MainViewModel(
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
