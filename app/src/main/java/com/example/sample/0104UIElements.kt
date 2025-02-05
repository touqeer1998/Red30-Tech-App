package com.example.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Greeting0104(
    modifier: Modifier = Modifier,
    name: String
) {
    Column {
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
