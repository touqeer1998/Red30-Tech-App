package com.example.red30.data

import com.example.red30.data.ConferenceDataUiState.Loaded

sealed class ConferenceDataUiState {
    data class Loaded(
        val day: Day = Day.Day1,
        val sessionInfos: List<SessionInfo> = emptyList(),
        val selectedSession: SessionInfo? = null
    ): ConferenceDataUiState()

    object Loading: ConferenceDataUiState()
}

val Loaded.speakers: List<Speaker>
    get() = sessionInfos.map { it.speaker }.distinctBy { it.id }

val Loaded.favorites: List<SessionInfo>
    get() = sessionInfos.filter { it.isFavorite == true }

val Loaded.sessionInfosByDay: List<SessionInfo>
    get() = sessionInfos.filter { it.day == day }
