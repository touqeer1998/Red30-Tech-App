package com.example.red30.compose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake

@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo,
    onSessionClick: (sessionId: Int) -> Unit = {},
    onFavoriteClick: (sessionId: Int) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                onSessionClick(sessionInfo.session.id)
            }
            .testTag("ui:sessionItem"),
        shape = RoundedCornerShape(0.dp)
    ) {
        with(sessionInfo) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    SessionTags(
                        track = session.track,
                        roomName = session.roomName
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = session.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SpeakerImage(
                            speaker = speaker,
                            imageSize = 48.dp
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

@PreviewLightDark
@Composable
private fun SessionItemPreview() {
    Red30TechTheme {
        SessionItem(
            sessionInfo = SessionInfo.fake(),
            onSessionClick = {}
        )
    }
}
