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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun Color0203(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            24.dp, Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var checked by remember { mutableStateOf(true) }

        Switch(
            checked = checked,
            onCheckedChange = { checked = !checked }
        )

        // TODO: update the color
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = !checked },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.tertiary
            )
        )

        Slider(value = .6F, onValueChange = {})

        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }

        // TODO: update the color
        val oneShotColor = Color(0xFFA23F16)
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = oneShotColor
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

@Preview(showBackground = true)
@Composable
private fun MaterialTheme0202Preview() {
    Red30TechTheme {
        Color0203()
    }
}
