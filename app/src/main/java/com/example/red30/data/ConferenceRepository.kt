package com.example.red30.data

import android.content.Context
import android.util.Log
import com.example.red30.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val TAG = "ConferenceRepository"

class ConferenceRepository(private val context: Context) {

    private var sessionInfos: List<SessionInfo> = emptyList()

    val speakers: Flow<List<Speaker>>
        get() = flow {
            emit(loadConferenceInfo().map { it.speaker }.distinctBy { it.id })
        }

    val favoriteSessions: Flow<List<SessionInfo>>
        get() = flow {
            emit(loadConferenceInfo().filter { it.isFavorite == true })
        }

    fun getSessionInfosByDay(day: Day = Day.Day1): Flow<List<SessionInfo>> = flow {
        emit(loadConferenceInfo().filter { it.day == day })
    }

    suspend fun loadConferenceInfo(): List<SessionInfo> = withContext(Dispatchers.IO) {
        if (sessionInfos.isNotEmpty()) return@withContext sessionInfos
        Log.d(TAG, "loading the conference info")

        val json = Json { ignoreUnknownKeys = true }
        val favoriteIds = listOf(5, 6, 7)

        sessionInfos = try {
            val conferenceData = context.resources.openRawResource(R.raw.conference_session_info)
                .bufferedReader()
                .use { json.decodeFromString<ConferenceData>(it.readText()) }

            conferenceData.sessions
                .map { session ->
                    SessionInfo(
                        session = session,
                        speaker = conferenceData.speakers.first { session.speakerId == it.id },
                        day = session.day,
                        isFavorite = favoriteIds.contains(session.id)
                    )
                }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            emptyList<SessionInfo>()
        }
        return@withContext sessionInfos
    }
}
