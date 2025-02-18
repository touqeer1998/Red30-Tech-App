package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.ui.component.LoadingIndicator
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake3
import com.example.red30.data.favorites

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onSessionClick: (sessionId: Int) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {},
    onScrollComplete: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        val listState = rememberLazyGridState()

        LaunchedEffect(uiState) {
            if (uiState.shouldAnimateScrollToTop) {
                listState.animateScrollToItem(0)
            }
        }

        if (listState.isScrollInProgress) {
            DisposableEffect(Unit) {
                onDispose { onScrollComplete() }
            }
        }

        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.favorites.isEmpty() -> EmptyFavorites()
            else -> FavoritesList(
                listState = listState,
                favorites = uiState.favorites,
                onSessionClick = onSessionClick,
                onFavoriteClick = onFavoriteClick,
            )
        }
    }
}

@Composable
private fun FavoritesList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    favorites: List<SessionInfo>,
    onSessionClick: (sessionId: Int) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(1),
        state = listState
    ) {
        items(favorites) { sessionInfo ->
            SessionItem(
                sessionInfo = sessionInfo,
                onSessionClick = onSessionClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
private fun EmptyFavorites(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            imageVector = Icons.Filled.BookmarkAdd,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.favorites_here),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview() {
    Red30TechTheme {
        FavoritesScreen(
            uiState = ConferenceDataUiState(
                sessionInfos = listOf(
                    SessionInfo.fake().copy(isFavorite = true),
                    SessionInfo.fake3().copy(isFavorite = true),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    Red30TechTheme {
        FavoritesScreen(
            uiState = ConferenceDataUiState(
                isLoading = false
            )
        )
    }
}
