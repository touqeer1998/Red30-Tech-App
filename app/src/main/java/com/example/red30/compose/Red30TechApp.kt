package com.example.red30.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Scaffold { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {

            }
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
