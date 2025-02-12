package com.example.red30.compose.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun SessionTags(
    modifier: Modifier = Modifier,
    track: String,
    roomName: String
) {
    FlowRow(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = track,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.sessionTag(
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = roomName,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.sessionTag(
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
        )
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
private fun SessionTagsPreview() {
    Red30TechTheme {
        Surface {
            SessionTags(
                track = "AI for Beginners",
                roomName = "201"
            )
        }
    }
}
