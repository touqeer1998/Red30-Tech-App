package com.example.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Greeting0104(
    modifier: Modifier = Modifier,
    name: String
) {
    Box(
        modifier = modifier.height(400.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            modifier = modifier,
            text = "Hello $name!",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Having fun with Compose?"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Greeting0104Preview() {
    Greeting0104(name = "Android")
}
