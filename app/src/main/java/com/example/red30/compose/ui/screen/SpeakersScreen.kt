package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
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
    shouldAnimateScrollToTop: Boolean = false
) {
    val listState = rememberLazyGridState()
    LaunchedEffect(shouldAnimateScrollToTop) {
        if (shouldAnimateScrollToTop) {
            listState.animateScrollToItem(0)
        }
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    if (!uiState.isLoading) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = rememberColumns(windowSizeClass),
            state = listState
        ) {
            items(
                uiState.speakers,
                key = { it.id }
            ) {
                if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    SpeakerItem(speaker = it)
                } else {
                    PortraitSpeakerItem(speaker = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PortraitSpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
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

@Composable
private fun rememberColumns(windowSizeClass: WindowSizeClass) = remember(windowSizeClass) {
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> GridCells.Fixed(1)
        WindowWidthSizeClass.MEDIUM -> GridCells.Fixed(2)
        else -> GridCells.Adaptive(320.dp)
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
