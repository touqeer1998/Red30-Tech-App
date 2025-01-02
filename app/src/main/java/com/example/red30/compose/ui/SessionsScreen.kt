package com.example.red30.compose.ui

import android.R.attr.text
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.red30.data.Session
import com.example.red30.ui.theme.Red30TechTheme

@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    sessions: List<Session>,
    onSessionClick: (Session) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(sessions) {
            SessionItem(
                session = it,
                onSessionClick = onSessionClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    session: Session,
    onSessionClick: (Session) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                onSessionClick(session)
            },
        shape = RoundedCornerShape(0.dp)
    ) {
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    text = session.name,
                    style = MaterialTheme.typography.titleLarge,
                )
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
private fun SessionsScreenPreview() {
    Red30TechTheme {
        SessionItem(
            session = Session(
                id = 1,
                speakerId = 1,
                name = "AI for Beginners",
                description = "Lorem Imps um",
                track = "Artificial Intelligence",
                roomName = "Room 201"
            )
        )
    }
}
