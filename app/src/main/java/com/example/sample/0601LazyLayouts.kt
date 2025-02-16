package com.example.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.fakes

private class LazyLayouts0601 {

    @Composable
    fun MainApp(modifier: Modifier = Modifier) {
        val uiState = ConferenceDataUiState.fakes()

        Scaffold { innerPadding ->
//            LazyColumn(
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//                    .padding(24.dp),
//                verticalArrangement = Arrangement.spacedBy(24.dp),
//            ) {
////                item {
////                    SessionItem(
////                        sessionInfo = uiState.sessionInfos.first()
////                    )
////                }
////
////                items(count = 5) {
////                    SessionItem(
////                        sessionInfo = uiState.sessionInfos.first()
////                    )
////                }
//
//                items(uiState.sessionInfos) {
//                    SessionItem(
//                        sessionInfo = it
//                    )
//                }
//
//            }


            LazyHorizontalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(48.dp),
            ) {
                items(count = 5) {
                    Box(modifier = Modifier.width(360.dp)) {
                        SessionItem(
                            modifier = Modifier.fillMaxWidth(),
                            sessionInfo = uiState.sessionInfos.last(),
                            onSessionClick = {}
                        )
                    }
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
