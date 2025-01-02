package com.example.red30.data

import android.content.Context
import android.util.Log
import com.example.red30.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

private const val TAG = "ConferenceRepository"

class ConferenceRepository(private val context: Context) {

    private var sessionInfo: SessionInfo? = null

    val sessions: Flow<List<Session>>
        get() = flow { emit(sessionInfo?.sessions.orEmpty()) }

    val speakers: Flow<List<Speaker>>
        get() = flow { emit(sessionInfo?.speakers.orEmpty()) }

    fun loadConferenceInfo() {
        val json = Json { ignoreUnknownKeys = true }
        sessionInfo = try {
            context.resources.openRawResource(R.raw.conference_session_info)
                .bufferedReader()
                .use { json.decodeFromString<SessionInfo>(it.readText()) }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            SessionInfo(
                speakers = emptyList(),
                sessions = emptyList()
            )
        }
    }
}
