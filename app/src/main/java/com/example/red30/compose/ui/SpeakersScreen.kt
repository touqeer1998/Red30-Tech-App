package com.example.red30.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.red30.data.Speaker
import com.example.red30.ui.theme.AvatarColors
import com.example.red30.ui.theme.Red30TechTheme

@Composable
fun SpeakersScreen(
    modifier: Modifier = Modifier,
    speakers: List<Speaker>,
    onSpeakerClick: (Speaker) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(speakers) {
            SpeakerItem(
                speaker = it,
                onSpeakerClick = onSpeakerClick
            )
        }
    }
}

@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
    onSpeakerClick: (Speaker) -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                onSpeakerClick(speaker)
            },
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

@Composable
private fun SpeakerImage(
    modifier: Modifier = Modifier,
    speaker: Speaker
) {
    val (size, setSize) = remember { mutableStateOf(IntSize.Zero) }
    if (speaker.imageUrl.isNullOrBlank()) {
        val backgroundColor = remember { pickBackgroundColorForName(speaker.name) }
        val initial = speaker.name.first().uppercase()
        Box(
            modifier = modifier
                .padding(16.dp)
                .size(100.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .onSizeChanged { setSize(it) },
            contentAlignment = Alignment.Center
        ) {
            val parentSizeDp = with(LocalDensity.current) { size.height.toDp() }.value
            val fontSize = parentSizeDp * TEXT_TO_PARENT_SIZE_RATIO
            val initialsFont = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = fontSize.sp,
                lineHeight = fontSize.sp,
                color = Color.White,
            )
            Text(
                text = initial,
                style = initialsFont
            )
        }
    } else {
        Box(
            modifier = modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(speaker.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
                    .size(100.dp),
            )
        }
    }
}

const val TEXT_TO_PARENT_SIZE_RATIO = 0.6

fun pickBackgroundColorForName(name: String?) =
    AvatarColors[name.hashCode().mod(AvatarColors.size - 1)]

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
