package com.example.red30.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    onSessionClick: (SessionInfo) -> Unit = {},
    onDayClick: (Day) -> Unit  = {}
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
            items(sessions) {
                SessionItem(
                    sessionInfo = it,
                    onSessionClick = onSessionClick
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo,
    onSessionClick: (SessionInfo) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                onSessionClick(sessionInfo)
            },
        shape = RoundedCornerShape(0.dp)
    ) {
        with(sessionInfo) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    FlowRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = session.track,
                            modifier = Modifier.sessionTag(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                        Text(
                            text = session.roomName,
                            modifier = Modifier.sessionTag(
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = session.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SpeakerImage(
                            speaker = speaker,
                            imageSize = 50.dp
                        )
                        Text(
                            text = speaker.name,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                var checked by remember { mutableStateOf(false) }
                IconToggleButton(
                    checked = checked,
                    onCheckedChange = { checked = it }
                ) {
                    if (checked) {
                        Icon(Icons.Filled.Favorite, contentDescription = "un-favorite session")
                    } else {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite session")
                    }
                }
            }
        }
    }
}

fun Modifier.sessionTag(color: Color) =
    this then Modifier.border(
        border = BorderStroke(
            width = 1.dp,
            color = color
        )
    ) then Modifier.padding(
        horizontal = 6.dp,
        vertical = 4.dp,
    )

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

@PreviewLightDark
@Composable
private fun SessionItemPreview() {
    Red30TechTheme {
        SessionItem(
            sessionInfo = SessionInfo(
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
    }
}
