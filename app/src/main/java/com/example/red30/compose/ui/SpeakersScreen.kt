package com.example.red30.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Speaker
import com.example.red30.data.speakers
import com.example.red30.ui.theme.Red30TechTheme

@Composable
fun SpeakersScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
) {
    if (!uiState.isLoading) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(
                uiState.speakers,
                key = { speaker ->
                    speaker.id
                }
            ) {
                SpeakerItem(speaker = it)
            }
        }
    }
}

@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
) {
    ElevatedCard(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Column {
            Row {
                SpeakerImage(speaker = speaker)

                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.titleLarge,
                        text = speaker.name,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 4.dp),
                        fontWeight = FontWeight.Bold,
                        text = speaker.title,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontStyle = FontStyle.Italic,
                        text = speaker.organization,
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                text = speaker.bio,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SpeakersScreenPreview() {
    Red30TechTheme {
        SpeakerItem(
            speaker = Speaker(
                id = 1,
                name = "Alycia Jones",
                title = "VP of Engineering",
                bio = "She's a superstar!",
                organization = "Binaryville"
            )
        )
    }
}
