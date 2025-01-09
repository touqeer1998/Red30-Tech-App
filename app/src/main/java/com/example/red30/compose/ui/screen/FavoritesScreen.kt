package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.fakes
import com.example.red30.data.favorites
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    shouldAnimateScrollToTop: Boolean = false,
    onSessionClick: (Int) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    Column {
        val listState = rememberLazyGridState()
        LaunchedEffect(shouldAnimateScrollToTop) {
            if (shouldAnimateScrollToTop) {
                listState.animateScrollToItem(0)
            }
        }

        if (!uiState.isLoading) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 250.dp),
                state = listState
            ) {
                items(
                    uiState.favorites,
                    key = { it.session.id }
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
