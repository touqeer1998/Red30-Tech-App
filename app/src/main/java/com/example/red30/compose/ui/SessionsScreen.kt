package com.example.red30.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.ui.theme.Red30TechTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    sessions: List<SessionInfo>,
    onSessionClick: (Int) -> Unit = {},
    onDayClick: (Day) -> Unit  = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    Column {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

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

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState
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

data class DayTabItem(
    val day: Day,
    val labelResourceId: Int
)

val dayTabItems = listOf(
    DayTabItem(day = Day.Day1, labelResourceId = R.string.day_1_label),
    DayTabItem(day = Day.Day2, labelResourceId = R.string.day_2_label)
)

@Preview(showBackground = true)
@Composable
private fun SessionScreenPreview() {
    Red30TechTheme {
        SessionsScreen(
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
                    day = Day.Day1
                )
            )
        )
    }
}
