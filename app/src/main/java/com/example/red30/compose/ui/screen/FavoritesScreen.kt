package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.example.red30.R
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.rememberColumns
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.MainAction
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fakes
import com.example.red30.data.favorites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onAction: (action: MainAction) -> Unit = {}
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
                onDispose { onAction(MainAction.OnScrollComplete) }
            }
        }

        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.favorites.isEmpty() -> EmptyFavorites()
            else -> FavoritesList(
                favorites = uiState.favorites,
                listState = listState,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun FavoritesList(
    modifier: Modifier = Modifier,
    favorites: List<SessionInfo>,
    listState: LazyGridState = rememberLazyGridState(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    onAction: (action: MainAction) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = rememberColumns(windowSizeClass),
        state = listState
    ) {
        items(favorites, key = { it.session.id }) { sessionInfo ->
            SessionItem(
                sessionInfo = sessionInfo,
                onAction = onAction
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
            uiState = ConferenceDataUiState.fakes().copy(
                sessionInfos = listOf(
                    SessionInfo.fake().copy(isFavorite = true)
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
