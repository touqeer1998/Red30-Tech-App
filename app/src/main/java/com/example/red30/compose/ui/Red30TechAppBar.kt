package com.example.red30.compose.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Red30TechAppBar(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    onNavigationIconClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    onFavoriteSessionClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (currentScreen.labelResourceId != 0) {
                Text(stringResource(currentScreen.labelResourceId))
            }
        },
        navigationIcon = {
            if (!topLevelScreens.contains(currentScreen)) {
                IconButton(onClick = { onNavigationIconClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        actions = actions
    )
}
