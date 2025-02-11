package com.example.sample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
private fun Remember0304(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 24.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        var numAttendees = mutableIntStateOf(0)
        Tag(
            tag = "Android Basics",
            numAttendees = numAttendees.intValue,
            onTagClick = { numAttendees.intValue++ }
        )

        var numDoubleAttendees by remember { mutableIntStateOf(1) }
        Tag(
            tag = "AI for Beginners",
            numAttendees = numDoubleAttendees,
            onTagClick = {
                numDoubleAttendees = numDoubleAttendees * 2
            }
        )
    }
}

@Composable
private fun Tag(
    modifier: Modifier = Modifier,
    tag: String,
    numAttendees: Int,
    onTagClick: () -> Unit
) {
    Text(
        text = "$tag --> Attendees: $numAttendees",
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .tag()
            .clickable { onTagClick() }
    )
}

@Composable
private fun Modifier.tag() =
    this then Modifier.border(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) then Modifier.padding(
        horizontal = 6.dp,
        vertical = 4.dp,
    )

@Preview
@Composable
private fun Remember0304Preview() {
    Red30TechTheme {
        Surface {
            Remember0304()
        }
    }
}
