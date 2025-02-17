package com.example.red30.compose.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake2
import com.example.red30.data.fake4

@Suppress("unused")
class SessionScreenShotTests {

    @PreviewLightDark
    @Composable
    fun SessionItemPreview() {
        Red30TechTheme {
            SessionItem(
                sessionInfo = SessionInfo.fake4()
            )
        }
    }
}
