package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SpeakerImage
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.speakers

@Composable
fun SpeakersScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onScrollComplete: () -> Unit = {}
) {
    val listState = rememberLazyGridState()

    LaunchedEffect(uiState) {
        if (uiState.shouldAnimateScrollToTop) {
            listState.animateScrollToItem(0)
        }
    }

    if (listState.isScrollInProgress) {
        DisposableEffect(Unit) {
            onDispose { onScrollComplete() }
        }
    }

    if (!uiState.isLoading && uiState.speakers.isEmpty()) {
        EmptyConferenceData(modifier = modifier)
    } else {
        SpeakersList(
            modifier = modifier,
            speakers = uiState.speakers,
            listState = listState
        )
    }
}

@Composable
private fun SpeakersList(
    modifier: Modifier = Modifier,
    speakers: List<Speaker>,
    listState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(330.dp),
        state = listState
    ) {
        items(speakers, key = { it.id }) {
            BoxWithConstraints {
                if (this.maxWidth > 360.dp) {
                    SpeakerItem(speaker = it)
                } else {
                    PortraitSpeakerItem(speaker = it)
                }
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
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("ui:speakerItem"),
        shape = RoundedCornerShape(0.dp),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpeakerImage(speaker = speaker)
                SpeakerDetails(speaker = speaker)
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = speaker.bio,
            )
        }
    }
}

@Composable
fun PortraitSpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("ui:portrait-speakerItem"),
        shape = RoundedCornerShape(0.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpeakerImage(
                speaker = speaker,
                imageSize = 88.dp
            )
            SpeakerDetails(
                modifier = Modifier.fillMaxWidth(),
                shouldCenter = true,
                speaker = speaker
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = speaker.bio,
            )
        }
    }
}

@Composable
fun SpeakerDetails(
    modifier: Modifier = Modifier,
    shouldCenter: Boolean = false,
    speaker: Speaker
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (shouldCenter)
            Alignment.CenterHorizontally
        else
            Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            style = LocalTextStyle.current.merge(
                MaterialTheme.typography.headlineSmall
            ),
            text = speaker.name,
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            text = speaker.title,
        )
        Text(
            fontStyle = FontStyle.Italic,
            text = speaker.organization,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun SpeakersScreenPreview() {
    Red30TechTheme {
        SpeakersScreen(
            uiState = ConferenceDataUiState(
                sessionInfos = listOf(
                    SessionInfo.fake(),
                    SessionInfo.fake2(),
                    SessionInfo.fake3()
                )
            )
        )
    }
}
