package com.example.red30.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.red30.MainViewModel
import com.example.red30.compose.ui.Red30TechAppBar
import com.example.red30.compose.ui.Red30TechBottomBar
import com.example.red30.compose.ui.Red30TechNavHost
import com.example.red30.compose.ui.Screen
import com.example.red30.ui.theme.Red30TechTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun Red30TechApp(modifier: Modifier = Modifier) {
    Red30TechTheme {
        KoinAndroidContext {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val currentScreen: Screen = Screen.valueOf(
                currentDestination?.route ?: Screen.Sessions.route
            )

            val viewModel: MainViewModel = koinViewModel<MainViewModel>()

            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    Red30TechAppBar(
                        currentScreen = currentScreen,
                        onNavigationIconClick = {
                            navController.navigateUp()
                        }
                    )
                },
                bottomBar = {
                    Red30TechBottomBar(
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            ) { innerPadding ->
                Red30TechNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
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
