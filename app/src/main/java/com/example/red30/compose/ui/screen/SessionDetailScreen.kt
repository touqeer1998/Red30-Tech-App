package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.red30.compose.ui.component.SessionTags
import com.example.red30.compose.ui.component.SessionTime
import com.example.red30.compose.ui.component.SpeakerImage
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.data.fake2

@Composable
fun SessionDetailScreen(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        with(sessionInfo) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                SessionHeader(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 360.dp),
                    sessionInfo = this@with,
                )
                SessionTime(
                    modifier = Modifier.width(340.dp),
                    session = session
                )
            }
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

@Composable
fun SessionHeader(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo
) {
    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        with(sessionInfo) {
            SessionTags(
                track = session.track,
                roomName = session.roomName
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                text = session.name,
                style = MaterialTheme.typography.headlineLarge,
            )
            SpeakerInfo(speaker = speaker)
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

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Preview
@Composable
private fun SessionDetailScreenPreview() {
    Red30TechTheme {
        Surface {
            SessionDetailScreen(
                sessionInfo = SessionInfo.fake2()
            )
        }
    }
}
