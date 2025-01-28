package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import com.example.red30.data.fake5
import com.example.red30.data.fake6
import com.example.red30.data.sessionInfosByDay

@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onDayClick: (day: Day) -> Unit = {},
    onScrollComplete: () -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {},
    navigateToSessionDetail: (sessionId: Int) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
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
            uiState.sessionInfos.isEmpty() -> EmptyConferenceData()
            else -> SessionsList(
                sessionInfos = uiState.sessionInfosByDay,
                listState = listState,
                onDayClick = onDayClick,
                onFavoriteClick = onFavoriteClick,
                navigateToSessionDetail = navigateToSessionDetail
            )
        }
    }
}

@Composable
fun SessionsList(
    modifier: Modifier = Modifier,
    sessionInfos: List<SessionInfo>,
    listState: LazyGridState = rememberLazyGridState(),
    onDayClick: (day: Day) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {},
    navigateToSessionDetail: (sessionId: Int) -> Unit = {}
) {
    var selectedChipIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(330.dp),
        state = listState
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                dayChipItems.forEachIndexed { index, tabItem ->
                    FilterChip(
                        selected = selectedChipIndex == index,
                        onClick = {
                            selectedChipIndex = index
                            onDayClick(tabItem.day)
                        },
                        label = {
                            Text(stringResource(tabItem.labelResourceId))
                        },
                    )
                }
            }
        }

        items(sessionInfos, key = { it.session.id }) { sessionInfo ->
            SessionItem(
                modifier = Modifier
                    .testTag("ui:sessionItem"),
                sessionInfo = sessionInfo,
                onFavoriteClick = onFavoriteClick,
                navigateToSessionDetail = navigateToSessionDetail
            )
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text(stringResource(R.string.loading))
    }
}

data class DayChipItem(
    val day: Day,
    val labelResourceId: Int
)

val dayChipItems = listOf(
    DayChipItem(day = Day.Day1, labelResourceId = R.string.day_1_label),
    DayChipItem(day = Day.Day2, labelResourceId = R.string.day_2_label)
)

@PreviewScreenSizes
@Composable
private fun SessionScreenPreview() {
    Red30TechTheme {
        SessionsScreen(
            uiState = ConferenceDataUiState(
                sessionInfos = listOf(
                    SessionInfo.fake(),
                    SessionInfo.fake3(),
                    SessionInfo.fake4(),
                    SessionInfo.fake5(),
                    SessionInfo.fake6(),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SessionScreenLoadingPreview() {
    Red30TechTheme {
        Surface {
            SessionsScreen(
                uiState = ConferenceDataUiState(isLoading = true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionScreenErrorMessagePreview() {
    Red30TechTheme {
        Surface {
            SessionsScreen(
                uiState = ConferenceDataUiState(
                    isLoading = false,
                    errorMessage = R.string.unable_to_load_conference_data_error
                )
            )
        }
    }
}
