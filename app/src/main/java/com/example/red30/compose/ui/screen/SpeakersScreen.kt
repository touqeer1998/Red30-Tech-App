package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.red30.R
import com.example.red30.compose.ui.component.EmptyConferenceData
import com.example.red30.compose.ui.component.SpeakerImage
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.Speaker
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import com.example.red30.data.fake5
import com.example.red30.data.speakers

@Composable
fun SpeakersScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
    onScrollComplete: () -> Unit = {}
) {
    val listState = rememberLazyStaggeredGridState()

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

    if (!uiState.isLoading && uiState.speakers.isEmpty())
        EmptyConferenceData(modifier = modifier)
    else
        SpeakersList(
            modifier = modifier,
            speakers = uiState.speakers,
            listState = listState
        )
}

@Composable
private fun SpeakersList(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    speakers: List<Speaker>,
    listState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
) {
    BoxWithConstraints {
        val maxWidth = this@BoxWithConstraints.maxWidth
        val desiredItemSize = 400.dp
        val width = maxWidth / (maxWidth / desiredItemSize)
        val isNotExpandedWidth =
            windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED

        val columns = when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> StaggeredGridCells.Fixed(1)
            WindowWidthSizeClass.MEDIUM -> StaggeredGridCells.Fixed(2)
            else -> StaggeredGridCells.Adaptive(width)
        }

        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = columns,
            state = listState
        ) {
            items(speakers) {
                if (width <= desiredItemSize && isNotExpandedWidth)
                    PortraitSpeakerItem(speaker = it)
                else
                    SpeakerItem(speaker = it)
            }
        }
    }
}

@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker
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

@Composable
fun PortraitSpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
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
                style = MaterialTheme.typography.bodyLarge
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
        Surface {
            SpeakersScreen(
                uiState = ConferenceDataUiState(
                    sessionInfos = listOf(
                        SessionInfo.fake(),
                        SessionInfo.fake2(),
                        SessionInfo.fake3(),
                        SessionInfo.fake4(),
                        SessionInfo.fake5()
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun SpeakersScreenEmptyDataPreview() {
    Red30TechTheme {
        Surface {
            SpeakersScreen(
                uiState = ConferenceDataUiState(
                    isLoading = false,
                    errorMessage = R.string.unable_to_load_conference_data_error
                )
            )
        }
    }
}
