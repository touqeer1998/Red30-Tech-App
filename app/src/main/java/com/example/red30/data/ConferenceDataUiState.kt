package com.example.red30.data

data class ConferenceDataUiState(
    val day: Day = Day.Day1,
    val sessionInfos: List<SessionInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val snackbarMessage: Int? = null,
    val shouldAnimateScrollToTop: Boolean = false,
) {
    companion object
}

val ConferenceDataUiState.speakers: List<Speaker>
    get() = sessionInfos.map { it.speaker }.distinctBy { it.id }

val ConferenceDataUiState.favorites: List<SessionInfo>
    get() = sessionInfos.filter { it.isFavorite == true }

val ConferenceDataUiState.sessionInfosByDay: List<SessionInfo>
    get() = sessionInfos.filter { it.day == day }

fun ConferenceDataUiState.getSelectedSession(sessionId: Int): SessionInfo? {
    return sessionInfos.find { it.session.id == sessionId }
}

fun ConferenceDataUiState.Companion.fakes() = ConferenceDataUiState(
    sessionInfos = listOf(
        SessionInfo.fake(),
        SessionInfo.fake2(),
        SessionInfo.fake3()
    )
)
