package com.example.red30.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.ui.theme.Red30TechTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo,
    onSessionClick: (Int) -> Unit = {},
    onFavoriteClick: (Int) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                onSessionClick(sessionInfo.session.id)
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

                IconToggleButton(
                    checked = sessionInfo.isFavorite,
                    onCheckedChange = { onFavoriteClick(sessionInfo.session.id) }
                ) {
                    if (sessionInfo.isFavorite) {
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