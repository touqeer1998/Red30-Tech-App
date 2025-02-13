package com.example.red30.compose.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.red30.compose.ui.screen.topLevelScreens

@Composable
fun Red30TechBottomBar(modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        topLevelScreens.forEach { screen ->
            val label = stringResource(screen.labelResourceId)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(label)
                },
                selected = false,
                onClick = {}
            )
        }
    }
}
