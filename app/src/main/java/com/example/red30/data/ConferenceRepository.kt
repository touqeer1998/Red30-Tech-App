package com.example.red30.data

import android.content.Context
import com.example.red30.R
import kotlinx.serialization.json.Json.Default.decodeFromString

class ConferenceRepository(private val context: Context) {

    private fun getConferenceInfo(): SessionInfo {
        return try {
            context.resources.openRawResource(R.raw.conference_session_info)
                .bufferedReader()
                .use { decodeFromString<SessionInfo>(it.readText()) }
        } catch (_: Exception) {
            SessionInfo(
                speakers = emptyList(),
                sessions = emptyList()
            )
        }
    }

    suspend fun loadConferenceInfo() {
        TODO()
    }
}
