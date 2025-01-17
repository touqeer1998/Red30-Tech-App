package com.example.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem

private class LazyLayouts0601 {

    @Composable
    fun MainApp(modifier: Modifier = Modifier) {
        // This will use the koinViewModel function in practice
        val viewModel = MainViewModel2(conferenceRepository = FakeConferenceRepository())
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Scaffold { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                item { // add a single item
                    SessionItem(
                        sessionInfo = uiState.sessionInfos.first()
                    )
                }
                items(count = 5) { // add multiple items
                    SessionItem(
                        sessionInfo = uiState.sessionInfos.first()
                    )
                }
                items(uiState.sessionInfos) { // extension function that allows you to add collections of items
                    SessionItem(sessionInfo = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun LazyLayouts0601Preview() {
    Red30TechTheme {
        LazyLayouts0601().MainApp()
    }
}
