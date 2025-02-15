package com.example.red30.compose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.R
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        uiState.sessionInfos.forEach {
            Text(it.session.name)
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
