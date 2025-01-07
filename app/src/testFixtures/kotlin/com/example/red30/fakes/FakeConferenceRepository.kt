package com.example.red30.fakes

import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2

class FakeConferenceRepository : ConferenceRepository {

    val favoriteIds: MutableList<Int> = mutableListOf()
    var throwLoadInfoException: Boolean = false
    var throwFavoritesException: Boolean = false

    override suspend fun loadConferenceInfo(): List<SessionInfo> {
        if (throwLoadInfoException) throw Exception()

        return listOf(SessionInfo.fake(), SessionInfo.fake2())
    }

    override suspend fun toggleFavorite(sessionId: Int): List<Int> {
        if (throwFavoritesException) throw Exception()

        if (favoriteIds.contains(sessionId))
            favoriteIds.remove(sessionId)
        else
            favoriteIds.add(sessionId)
        return favoriteIds
    }
}
