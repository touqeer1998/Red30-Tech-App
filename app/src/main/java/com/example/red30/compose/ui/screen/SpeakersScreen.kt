package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SpeakerImage
import com.example.red30.compose.ui.rememberColumns
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.MainAction
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
    onAction: (action: MainAction) -> Unit = {},
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    val listState = rememberLazyGridState()

    LaunchedEffect(uiState) {
        if (uiState.shouldAnimateScrollToTop) {
            listState.animateScrollToItem(0)
        }
    }

    if (listState.isScrollInProgress) {
        DisposableEffect(Unit) {
            onDispose { onAction(MainAction.OnScrollComplete) }
        }
    }

    var maxHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

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
                    PortraitSpeakerItem(
                        speaker = it,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val height = with(density) {
                                    coordinates.size.height.toDp()
                                }
                                if (height > maxHeight) {
                                    maxHeight = height
                                }
                            }
                            .heightIn(min = maxHeight),
                    )
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
