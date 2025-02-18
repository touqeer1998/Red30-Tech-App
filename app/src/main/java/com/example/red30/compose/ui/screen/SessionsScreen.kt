package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.ui.component.EmptyConferenceData
import com.example.red30.compose.ui.component.LoadingIndicator
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.theme.Red30TechTheme
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
    onSessionClick: (sessionId: Int) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {},
    onDayClick: (day: Day) -> Unit = {},
    onScrollComplete: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
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
            else -> {
                SessionsList(
                    modifier = modifier,
                    listState = listState,
                    uiState = uiState,
                    onSessionClick = onSessionClick,
                    onFavoriteClick = onFavoriteClick,
                    onDayClick = onDayClick
                )
            }
        }
    }
}

@Composable
fun SessionsList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    uiState: ConferenceDataUiState,
    onSessionClick: (sessionId: Int) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {},
    onDayClick: (day: Day) -> Unit = {}
) {
    var selectedChipIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .testTag("ui:sessionsList"),
        columns = GridCells.Adaptive(330.dp),
        state = listState
    ) {
        item(span = { GridItemSpan(maxLineSpan) } ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                dayChipItems.forEachIndexed { index, chipItem ->
                    FilterChip(
                        selected = selectedChipIndex == index,
                        onClick = {
                            selectedChipIndex = index
                            onDayClick(chipItem.day)
                        },
                        label = {
                            Text(stringResource(chipItem.labelResourceId))
                        }
                    )
                }
            }
        }

        items(uiState.sessionInfosByDay) {
            SessionItem(
                sessionInfo = it,
                onSessionClick = onSessionClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

data class DayChipItem(
    val day: Day,
    val labelResourceId: Int
)

val dayChipItems = listOf(
    DayChipItem(Day.Day1, R.string.day_1_label),
    DayChipItem(Day.Day2, R.string.day_2_label),
)

@PreviewScreenSizes
@Composable
private fun SessionScreenPreview() {
    Red30TechTheme {
        Surface {
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
}

@Preview
@Composable
private fun SessionScreenLoadingPreview() {
    Red30TechTheme {
        Surface {
            SessionsScreen(
                uiState = ConferenceDataUiState(isLoading = true),
                onSessionClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun SessionScreenEmptyDataPreview() {
    Red30TechTheme {
        Surface {
            SessionsScreen(
                uiState = ConferenceDataUiState(
                    isLoading = false,
                    errorMessage = R.string.unable_to_load_conference_data_error
                ),
                onSessionClick = {}
            )
        }
    }
}
