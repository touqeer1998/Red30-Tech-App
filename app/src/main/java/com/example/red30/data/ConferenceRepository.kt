package com.example.red30.data

import android.content.Context
import android.util.Log
import com.example.red30.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val TAG = "ConferenceRepository"

class ConferenceRepository(private val context: Context) {

    private var conferenceData: ConferenceData? = null

    val speakers: Flow<List<Speaker>>
        get() = flow {
            while(conferenceData?.speakers.isNullOrEmpty()) {
                delay(150)
            }
            emit(conferenceData!!.speakers)
        }

    fun getSessionInfosByDay(day: Day = Day.Day1): Flow<List<SessionInfo>> = flow {
        while(conferenceData?.sessions.isNullOrEmpty() || conferenceData?.speakers.isNullOrEmpty()) {
            delay(150)
        }

        val sessionInfos = conferenceData!!.sessions
            .filter { it.day == day }
            .map { session ->
                SessionInfo(
                    session = session,
                    speaker = conferenceData!!.speakers.first { session.speakerId == it.id },
                    day = day
                )
            }
        Log.d(TAG, sessionInfos.size.toString())
        emit(sessionInfos)
    }

    suspend fun loadConferenceInfo() = withContext(Dispatchers.IO) {
        val json = Json { ignoreUnknownKeys = true }
        conferenceData = try {
            context.resources.openRawResource(R.raw.conference_session_info)
                .bufferedReader()
                .use { json.decodeFromString<ConferenceData>(it.readText()) }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            ConferenceData(
                speakers = emptyList(),
                sessions = emptyList()
            )
        }
    }
}
