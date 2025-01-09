package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.example.red30.R
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.rememberColumns
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake3
import com.example.red30.data.sessionInfosByDay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    shouldAnimateScrollToTop: Boolean = false,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    onSessionClick: (Int) -> Unit = {},
    onDayClick: (Day) -> Unit  = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val listState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(shouldAnimateScrollToTop) {
            if (shouldAnimateScrollToTop) {
                listState.animateScrollToItem(0)
            }
        }

        var maxHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        if (!uiState.isLoading) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = rememberColumns(windowSizeClass),
                state = listState
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        dayTabItems.forEachIndexed { index, tabItem ->
                            FilterChip(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    onDayClick(tabItem.day)
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                },
                                label = {
                                    Text(stringResource(tabItem.labelResourceId))
                                },
                            )
                        }
                    }
                }
                items(
                    uiState.sessionInfosByDay,
                    key = { it.session.id }
                ) { sessionInfo ->
                    val sessionId = sessionInfo.session.id
                    SessionItem(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val height = with(density) {
                                    coordinates.size.height.toDp()
                                }
                                if (height > maxHeight) {
                                    maxHeight = height
                                }
                            }
                            .heightIn(min = maxHeight),
                        sessionInfo = sessionInfo,
                        onSessionClick = { onSessionClick(sessionId) },
                        onFavoriteClick = { onFavoriteClick(sessionId) }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

data class DayTabItem(
    val day: Day,
    val labelResourceId: Int
)

val dayTabItems = listOf(
    DayTabItem(day = Day.Day1, labelResourceId = R.string.day_1_label),
    DayTabItem(day = Day.Day2, labelResourceId = R.string.day_2_label)
)

@Preview(showBackground = true, device = "spec:parent=pixel_8,orientation=landscape")
@Preview(showBackground = true)
@Composable
private fun SessionScreenPreview() {
    Red30TechTheme {
        SessionsScreen(
            uiState = ConferenceDataUiState(
                sessionInfos = listOf(SessionInfo.fake(), SessionInfo.fake3())
            )
        )
    }
}
