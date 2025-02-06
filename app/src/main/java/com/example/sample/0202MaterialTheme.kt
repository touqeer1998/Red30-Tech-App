package com.example.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MaterialTheme0202(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            24.dp, Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Switch(checked = true, onCheckedChange = {})

        Checkbox(checked = true, onCheckedChange = {})

        Slider(value = .6F, onValueChange = {})

        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }

        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
        )

        FilledTonalButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Button",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

private val DarkColorScheme = darkColorScheme(
    primary = Color.DarkGray,
    secondary = Color.Cyan,
    tertiary = Color.Magenta,
    primaryContainer = Color.DarkGray,
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.DarkGray,
    secondary = Color.Cyan,
    tertiary = Color.Magenta,
    primaryContainer = Color.DarkGray,
    onPrimaryContainer = Color.White
)

@Composable
private fun MaterialTheme0202Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun MaterialTheme0202Preview() {
    MaterialTheme0202Theme {
        MaterialTheme0202()
    }
}


@Preview(showBackground = true)
@Composable
private fun SampleThemePreview() {
    AppTheme0202 {
        MaterialTheme0202()
    }
}
