package com.example.red30.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.fakes
import com.example.red30.data.favorites
import com.example.red30.compose.theme.Red30TechTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onSessionClick: (Int) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    Column {
        if (!uiState.isLoading) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
            ) {
                items(
                    uiState.favorites,
                    key = { sessionInfo ->
                        sessionInfo.session.id
                    }
                ) { sessionInfo ->
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
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview() {
    Red30TechTheme {
        FavoritesScreen(
            uiState = ConferenceDataUiState.fakes()
        )
    }
}
