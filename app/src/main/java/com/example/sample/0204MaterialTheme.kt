package com.example.sample

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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun MaterialTheme0204(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
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
        // TODO: Wrap with theme
        MaterialTheme0204Theme {
            CircularProgressIndicator(modifier = Modifier.width(64.dp))

            Text("This is my special text")

            // TODO: Wrap with composition local
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    color = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("This is my very special text")

                Text("This is also my very special text")
            }

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
fun MaterialTheme0204Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@PreviewLightDark
@Composable
private fun MaterialTheme0204Preview() {
    Red30TechTheme {
        Surface {
            MaterialTheme0204()
        }
    }
}
