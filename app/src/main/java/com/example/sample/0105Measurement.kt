package com.example.sample

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Greeting0105(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Having fun with Compose?",
        modifier = modifier.padding(16.dp),
        fontSize = 40.sp
    )
}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_5,
    name = "Nexus 5"
)
@Composable
private fun Greeting0104Preview() {
    Greeting0105()
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    name = "Pixel 4 XL"
)
@Composable
private fun Greeting0104PixelPreview() {
    Greeting0105()
}
