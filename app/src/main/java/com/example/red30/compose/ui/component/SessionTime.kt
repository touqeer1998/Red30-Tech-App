package com.example.red30.compose.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.duration
import com.example.red30.data.fake

@Composable
fun SessionTime(
    modifier: Modifier = Modifier,
    session: Session
) {
    Column(
        modifier = modifier
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

@Preview
@Composable
private fun SessionTimePreview() {
    Red30TechTheme {
        Surface {
            SessionTime(session = Session.fake())
        }
    }
}