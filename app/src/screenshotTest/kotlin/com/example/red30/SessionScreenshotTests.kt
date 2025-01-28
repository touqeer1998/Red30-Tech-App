package com.example.red30

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.compose.ui.component.SessionItem
import com.example.red30.compose.ui.screen.SessionDetailScreen
import com.example.red30.compose.ui.screen.SessionsScreen
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fakes

@Suppress("unused")
class SessionScreenshotTests {

    @Preview(showBackground = true)
    @Composable
    fun SessionDetailScreenPreview() {
        Red30TechTheme {
            Surface {
                SessionDetailScreen(
                    sessionInfo = SessionInfo.fake()
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
                    uiState = ConferenceDataUiState.fakes()
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
                    uiState = ConferenceDataUiState.fakes()
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
                    uiState = ConferenceDataUiState.fakes()
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun SessionScreenErrorMessagePreview() {
        Red30TechTheme {
            Surface {
                SessionsScreen(
                    uiState = ConferenceDataUiState(
                        isLoading = false,
                        errorMessage = R.string.unable_to_load_conference_data_error
                    )
                )
            }
        }
    }
}
