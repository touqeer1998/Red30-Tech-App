package com.example.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.theme.Red30TechTheme

@Composable
fun State0302(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 48.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var checked = true
        var checkedState: MutableState<Boolean> = remember { mutableStateOf(true) }

        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = !checkedState.value }
        )

        Checkbox(
            checked = checked,
            onCheckedChange = { checked = !checked }
        )
    }
}

@Preview
@Composable
private fun State0302Preview() {
    Red30TechTheme {
        Surface {
            State0302()
        }
    }
}
