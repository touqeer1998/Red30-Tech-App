package com.example.red30.compose.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.red30.R
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.data.duration
import com.example.red30.data.fake
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionTags
import com.example.red30.compose.ui.component.SpeakerImage

@Composable
fun SessionDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.systemBars
                    .union(WindowInsets.displayCutout)
            )
    ) {
        if (!uiState.isLoading && uiState.selectedSession != null) {
            with(uiState.selectedSession) {
                item {
                    SessionTags(
                        track = session.track,
                        roomName = session.roomName
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = session.name,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
                item {
                    SpeakerInfo(speaker = speaker)
                }
                item {
                    SessionTime(session = session)
                }
                item {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = session.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            lineHeight = 28.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SpeakerInfo(
    modifier: Modifier = Modifier,
    speaker: Speaker
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpeakerImage(
            speaker = speaker,
            imageSize = 64.dp
        )
        Column {
            Text(
                text = speaker.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = speaker.title,
            )
        }
    }
}

@Composable
fun SessionTime(
    modifier: Modifier = Modifier,
    session: Session
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                border = BorderStroke(
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.tertiary, Color.Transparent),
                        tileMode = TileMode.Repeated,
                        start = Offset.Zero,
                        end = Offset(10f, 10f)
                    ),
                    width = 1.dp
                ),
            )
            .padding(16.dp),
    ) {
        TimeItem(
            modifier = Modifier.padding(8.dp),
            icon = Icons.Filled.CalendarToday,
            text = stringResource(
                if (session.day == Day.Day1)
                    R.string.day_1_label
                else
                    R.string.day_2_label
            ),
            labelId = R.string.day_label
        )
        TimeItem(
            modifier = Modifier.padding(8.dp),
            icon = Icons.Filled.Schedule,
            text = session.startTime,
            labelId = R.string.start_time_label
        )
        TimeItem(
            modifier = Modifier.padding(8.dp),
            icon = Icons.Filled.Schedule,
            text = session.endTime,
            labelId = R.string.end_time_label
        )
        TimeItem(
            modifier = Modifier.padding(8.dp),
            icon = Icons.Filled.HourglassEmpty,
            text = "${session.duration} minutes",
            labelId = R.string.time_duration_label
        )
    }
}

@Composable
fun TimeItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    labelId: Int
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = stringResource(R.string.time_icon)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(labelId),
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.width(12.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun SessionDetailScreenPreview() {
    Red30TechTheme {
        SessionDetailScreen(
            uiState = ConferenceDataUiState(
                selectedSession = SessionInfo.fake(),
            )
        )
    }
}
