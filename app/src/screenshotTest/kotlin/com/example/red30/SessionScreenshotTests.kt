package com.example.red30

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.red30.compose.ui.screen.SessionDetailScreen
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.data.Day
import com.example.red30.data.Session
import com.example.red30.data.fake3

@Suppress("unused")
class SessionScreenshotTests {

    @Preview(showBackground = true)
    @Composable
    fun SessionDetailScreenPreview() {
        Red30TechTheme {
            Surface {
                SessionDetailScreen(
                    uiState = ConferenceDataUiState(
                        selectedSession = SessionInfo.fake(),
                    )
                )
            }
        }
    }

    @PreviewLightDark
    @Composable
    fun SessionItemPreview() {
        Red30TechTheme {
            SessionItem(
                sessionInfo = SessionInfo.fake()
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SessionScreenLoadingPreview() {
        Red30TechTheme {
            Surface {
                SessionsScreen(
                    uiState = ConferenceDataUiState(isLoading = true)
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SessionScreenPreview() {
        Red30TechTheme {
            Surface {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        sessionInfos = listOf(SessionInfo.fake(), SessionInfo.fake2())
                    )
                )
            }
        }
    }

    @Preview(showBackground = true, device = "spec:orientation=landscape,width=411dp,height=891dp")
    @Composable
    private fun SessionScreenLandscapePreview() {
        Red30TechTheme {
            Surface {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        sessionInfos = listOf(SessionInfo.fake(), SessionInfo.fake3())
                    )
                )
            }
        }
    }

    @Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
    @Composable
    private fun SessionScreenTabletPreview() {
        Red30TechTheme {
            Surface {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        sessionInfos = listOf(
                            SessionInfo.fake(),
                            SessionInfo.fake2().copy(
                                day = Day.Day1
                            ),
                            SessionInfo.fake3(),
                            SessionInfo.fake3().copy(
                                session = Session.fake3()
                                    .copy(
                                        id = 55,
                                        name = "Hacking for Money"
                                    )
                            )
                        )
                    )
                )
            }
        }
    }
}
