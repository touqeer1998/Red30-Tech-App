package com.example.red30.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.ui.theme.Red30TechTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    sessions: List<SessionInfo>,
    onSessionClick: (Int) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    Column {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(sessions) { sessionInfo ->
                val sessionId = sessionInfo.session.id
                SessionItem(
                    sessionInfo = sessionInfo,
                    onSessionClick = { onSessionClick(sessionId) },
                    onFavoriteClick = { onFavoriteClick(sessionId) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview() {
    Red30TechTheme {
        FavoritesScreen(
            sessions = listOf(
                SessionInfo(
                    session = Session(
                        id = 1,
                        speakerId = 1,
                        name = "AI for Beginners",
                        description = "Lorem Imps um",
                        track = "Artificial Intelligence",
                        roomName = "Room 201"
                    ),
                    speaker = Speaker(
                        id = 1,
                        name = "Alycia Jones",
                        title = "VP of Engineering",
                        bio = "She's a superstar!",
                        organization = "Binaryville"
                    ),
                    day = Day.Day1,
                    isFavorite = true
                )
            )
        )
    }
}
