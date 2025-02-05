package com.example.red30.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.red30.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val TAG = "ConferenceRepository"

interface ConferenceRepository {
    suspend fun loadConferenceInfo(): List<SessionInfo>
    suspend fun toggleFavorite(sessionId: Int): List<Int>
}

val FAVORITE_IDS = stringPreferencesKey("favorite_ids")

class InMemoryConferenceRepository(
    private val appContext: Context,
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ConferenceRepository {

    override suspend fun toggleFavorite(sessionId: Int): List<Int> {
        val updatedFavorites: MutableList<Int> = getFavoriteIds().first().toMutableList()

        if (updatedFavorites.contains(sessionId)) {
            updatedFavorites.remove(sessionId)
        } else {
            updatedFavorites.add(sessionId)
        }

        dataStore.edit { preferences ->
            preferences[FAVORITE_IDS] = updatedFavorites.joinToString(",")
        }

        return updatedFavorites
    }

    private fun getFavoriteIds(): Flow<List<Int>> = dataStore.data.map { prefs ->
        prefs[FAVORITE_IDS]
            ?.split(",")
            ?.filterNot { it.isEmpty() }
            ?.map { it.toInt() }
            .orEmpty()
    }

    override suspend fun loadConferenceInfo(): List<SessionInfo> = withContext(dispatcher) {
        val json = Json { ignoreUnknownKeys = true }
        val favoriteIds: List<Int> = getFavoriteIds().first()

        try {
            val conferenceData = appContext.resources.openRawResource(R.raw.conference_session_info)
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
    }
}
