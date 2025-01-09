package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem
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

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            dayTabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onDayClick(tabItem.day)
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = stringResource(tabItem.labelResourceId)
                    )
                }
            }
        }

        if (!uiState.isLoading) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 250.dp),
                state = listState
            ) {
                items(
                    uiState.sessionInfosByDay,
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
