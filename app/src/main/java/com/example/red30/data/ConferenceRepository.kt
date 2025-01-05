package com.example.red30.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.red30.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val TAG = "ConferenceRepository"

interface ConferenceRepository {
    val speakers: Flow<List<Speaker>>
    val favoriteSessions: Flow<List<SessionInfo>>
    fun getSessionInfosByDay(day: Day = Day.Day1): Flow<List<SessionInfo>>
    suspend fun loadConferenceInfo(): List<SessionInfo>
    suspend fun getSessionInfoById(sessionId: Int): SessionInfo?
    suspend fun toggleFavorite(sessionId: Int): SessionInfo?
}

val FAVORITE_IDS = stringPreferencesKey("favorite_ids")

class InMemoryConferenceRepository(
    private val appContext: Context,
    private val dataStore: DataStore<Preferences>
): ConferenceRepository {

    private var sessionInfos: List<SessionInfo> = emptyList()

    override val speakers: Flow<List<Speaker>>
        get() = flow {
            emit(loadConferenceInfo().map { it.speaker }.distinctBy { it.id })
        }

    override val favoriteSessions: Flow<List<SessionInfo>>
        get() = flow {
            emit(loadConferenceInfo().filter { it.isFavorite == true })
        }

    override fun getSessionInfosByDay(day: Day): Flow<List<SessionInfo>> = flow {
        emit(loadConferenceInfo().filter { it.day == day })
    }

    override suspend fun getSessionInfoById(sessionId: Int): SessionInfo? {
        return loadConferenceInfo().find { it.session.id == sessionId }
    }

    override suspend fun toggleFavorite(sessionId: Int): SessionInfo? {
        val updatedFavorites: MutableList<Int> = getFavoriteIds().first().toMutableList()

        val isFavorited = if (updatedFavorites.contains(sessionId)) {
            updatedFavorites.remove(sessionId)
            false
        } else {
            updatedFavorites.add(sessionId)
            true
        }

        dataStore.edit { preferences ->
            preferences[FAVORITE_IDS] = updatedFavorites.joinToString(",")
        }

        return refreshSessionWithFavorites(sessionId = sessionId, isFavorited = isFavorited)
    }

    private fun getFavoriteIds(): Flow<List<Int>> = dataStore.data.map { prefs ->
        prefs[FAVORITE_IDS]
            ?.split(",")
            ?.filterNot { it.isEmpty() }
            ?.map { it.toInt() }
            .orEmpty()
    }

    private fun refreshSessionWithFavorites(sessionId: Int, isFavorited: Boolean): SessionInfo? {
        if (sessionInfos.isEmpty()) return null

        val session = sessionInfos.find { it.session.id == sessionId }
        session?.let { it.isFavorite = isFavorited }
        return session
    }

    override suspend fun loadConferenceInfo(): List<SessionInfo> = withContext(Dispatchers.IO) {
        if (sessionInfos.isNotEmpty()) return@withContext sessionInfos
        Log.d(TAG, "loading the conference info")

        val json = Json { ignoreUnknownKeys = true }
        val favoriteIds: List<Int> = getFavoriteIds().first()
        Log.d(TAG, "loaded the favorite ids: $favoriteIds")

        sessionInfos = try {
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
        return@withContext sessionInfos
    }
}
