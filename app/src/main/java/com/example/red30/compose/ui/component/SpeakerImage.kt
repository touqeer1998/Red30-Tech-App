package com.example.red30.compose.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.red30.R
import com.example.red30.compose.ui.theme.AvatarColors
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.Speaker
import com.example.red30.data.initial

private const val TEXT_TO_PARENT_SIZE_RATIO = 0.6

@Composable
fun SpeakerImage(
    modifier: Modifier = Modifier,
    imageSize: Dp = 100.dp,
    speaker: Speaker
) {
    if (speaker.imageUrl.isNullOrBlank()) {
        val backgroundColor = remember { pickBackgroundColorForName(speaker.name) }
        val initial = speaker.initial
        Box(
            modifier = modifier
                .padding(16.dp)
                .size(imageSize)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            val parentSizeDp = with(LocalDensity.current) { imageSize }.value
            val fontSize = parentSizeDp * TEXT_TO_PARENT_SIZE_RATIO
            val initialsFont = LocalTextStyle.current.copy(
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
            if (LocalInspectionMode.current) {
                AsyncImage(
                    model = R.drawable.placeholder_speaker_image,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(imageSize)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(speaker.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(imageSize)
                )
            }
        }
    }
}

fun pickBackgroundColorForName(name: String?) =
    AvatarColors[name.hashCode().mod(AvatarColors.size - 1)]

@Preview
@Composable
private fun SpeakerImageSmallPreview() {
    Red30TechTheme {
        SpeakerImage(
            imageSize = 50.dp,
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

@Preview
@Composable
private fun SpeakerImageUrlPreview() {
    Red30TechTheme {
        SpeakerImage(
            imageSize = 50.dp,
            speaker = Speaker(
                id = 1,
                name = "Alycia Jones",
                title = "VP of Engineering",
                imageUrl = "https://drumsgmkdq.an",
                bio = "She's a superstar!",
                organization = "Binaryville"
            )
        )
    }
}
