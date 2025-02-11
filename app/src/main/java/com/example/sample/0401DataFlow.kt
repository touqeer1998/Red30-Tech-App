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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

data class DataFlowUiState(
    val numAttendees: Int = 0,
    val numDoubleAttendees: Int = 0,
)

@Preview
@Composable
private fun DataFlow0401Preview() {
    Red30TechTheme {
        Surface {
            var numAttendees by remember { mutableIntStateOf(0) }
            var numDoubleAttendees by remember { mutableIntStateOf(1) }

            DataFlow0401(
                uiState = DataFlowUiState(
                    numAttendees = numAttendees,
                    numDoubleAttendees = numDoubleAttendees
                ),
                onAndroidBasicsClick = { numAttendees++ },
                onAIBeginnersClick = { numDoubleAttendees = numDoubleAttendees * 2 }
            )
        }
    }
}

@Composable
private fun DataFlow0401(
    modifier: Modifier = Modifier,
    uiState: DataFlowUiState,
    onAndroidBasicsClick: () -> Unit,
    onAIBeginnersClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 48.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Tag(
            modifier = Modifier.clickable {
                onAndroidBasicsClick()
            },
            tag = "Android Basics",
            numAttendees = uiState.numAttendees,
        )
        Tag(
            modifier = Modifier.clickable {
                onAIBeginnersClick()
            },
            tag = "AI for Beginners",
            numAttendees = uiState.numDoubleAttendees,
        )
    }
}

@Composable
private fun Tag(
    modifier: Modifier = Modifier,
    tag: String,
    numAttendees: Int,
) {
    Text(
        text = "$tag --> Attendees: $numAttendees",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .tag()
            .padding(16.dp)
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
