package com.example.red30.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.red30.MainViewModel
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.NavigationType
import com.example.red30.compose.ui.NavigationType.Companion.rememberNavigationType
import com.example.red30.compose.ui.Red30TechBottomBar
import com.example.red30.compose.ui.Red30TechContent
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun Red30TechApp(modifier: Modifier = Modifier) {
    Red30TechTheme {
        KoinAndroidContext {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val viewModel = koinViewModel<MainViewModel>(
                viewModelStoreOwner = LocalActivity.current as ComponentActivity
            )
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            var navigationType: NavigationType = rememberNavigationType(
                windowSizeClass.windowWidthSizeClass
            )

            Scaffold(
                modifier = modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                        Red30TechBottomBar(
                            navController = navController,
                            currentDestination = currentDestination,
                            onAction = viewModel::onMainAction
                        )
                    }
                }
            ) { innerPadding ->
                Red30TechContent(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    currentDestination = currentDestination,
                    paddingValues = innerPadding,
                    showNavigationRail = navigationType == NavigationType.RAIL,
                    onAction = viewModel::onMainAction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Red30TechAppPreview() {
    Red30TechApp()
}
