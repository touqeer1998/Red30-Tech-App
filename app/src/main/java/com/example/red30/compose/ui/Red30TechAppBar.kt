package com.example.red30.compose.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.red30.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Red30TechAppBar(
    modifier: Modifier = Modifier,
    screenTitle: String?,
    actions: @Composable RowScope.() -> Unit = {},
    onFavoriteSessionClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(screenTitle ?: stringResource(R.string.sessions_label))
        },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//            titleContentColor = MaterialTheme.colorScheme.onPrimary,
//            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
//        ),
        actions = actions
    )
}
