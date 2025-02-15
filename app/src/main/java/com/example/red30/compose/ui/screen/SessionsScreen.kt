package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import com.example.red30.data.fake5
import com.example.red30.data.fake6

@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    uiState: ConferenceDataUiState,
) {
    var selectedChipIndex by remember { mutableIntStateOf(0) }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(1)
    ) {
        item {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                dayChipItems.forEachIndexed { index, chipItem ->
                    FilterChip(
                        selected = selectedChipIndex == index,
                        onClick = {
                            selectedChipIndex = index
                        },
                        label = {
                            Text(stringResource(chipItem.labelResourceId))
                        }
                    )
                }

            }
        }

        items(uiState.sessionInfos) {
            SessionItem(
                sessionInfo = it
            )
        }
    }
}

data class DayChipItem(
    val day: Day,
    val labelResourceId: Int
)

val dayChipItems = listOf(
    DayChipItem(Day.Day1, R.string.day_1_label),
    DayChipItem(Day.Day2, R.string.day_2_label),
)

@Preview(showBackground = true)
@Composable
private fun SessionScreenPreview() {
    Red30TechTheme {
        SessionsScreen(
            uiState = ConferenceDataUiState(
                sessionInfos = listOf(
                    SessionInfo.fake(),
                    SessionInfo.fake3(),
                    SessionInfo.fake4(),
                    SessionInfo.fake5(),
                    SessionInfo.fake6(),
                )
            )
        )
    }
}
