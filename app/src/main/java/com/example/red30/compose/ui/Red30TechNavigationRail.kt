package com.example.red30.compose.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.screen.topLevelScreens

@Composable
fun Red30TechNavigationRail(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    currentDestination: NavDestination? = null,
    onActiveDestinationClick: () -> Unit = {},
) {
    NavigationRail(modifier = modifier) {
        Spacer(Modifier.weight(1f))

        topLevelScreens.forEach { screen ->
            val label = stringResource(id = screen.labelResourceId)
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(label)
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    if (screen.route != currentDestination?.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    } else {
                        onActiveDestinationClick.invoke()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun NavigationRailPreview() {
    Red30TechTheme {
        Red30TechNavigationRail()
    }
}
