package com.example.sample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.theme.Red30TechTheme

@Composable
private fun StateHoisting0303(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 24.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Tag(
            tag = "Android Basics"
        )
    }
}

@Composable
private fun Tag(
    modifier: Modifier = Modifier,
    tag: String,
) {
    var numAttendees = remember { mutableIntStateOf(0) }
    Text(
        text = "$tag --> Attendees: ${numAttendees.intValue}",
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .tag()
            .clickable { numAttendees.intValue++ }
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
private fun StateHoisting0303Preview() {
    Red30TechTheme {
        Surface {
            StateHoisting0303()
        }
    }
}
