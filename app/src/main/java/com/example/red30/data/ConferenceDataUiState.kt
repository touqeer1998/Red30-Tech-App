package com.example.red30.data

import com.example.red30.data.ConferenceDataUiState.Loaded

sealed class ConferenceDataUiState {
    data class Loaded(
        val day: Day = Day.Day1,
        val sessionInfos: List<SessionInfo> = emptyList(),
        val selectedSession: SessionInfo? = null
    ): ConferenceDataUiState() {
        companion object
    }

    object Loading: ConferenceDataUiState()
}

val Loaded.speakers: List<Speaker>
    get() = sessionInfos.map { it.speaker }.distinctBy { it.id }

val Loaded.favorites: List<SessionInfo>
    get() = sessionInfos.filter { it.isFavorite == true }

val Loaded.sessionInfosByDay: List<SessionInfo>
    get() = sessionInfos.filter { it.day == day }

fun Loaded.getSelectedSession(sessionId: Int): SessionInfo? {
    return sessionInfos.find { it.session.id == sessionId }
}

fun Loaded.Companion.fakes() = Loaded(
    sessionInfos = listOf(SessionInfo.fake(), SessionInfo.fake2()),
)
