package com.example.red30.fakes

import com.example.red30.data.ConferenceRepository
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake
import com.example.red30.data.fake2
import com.example.red30.data.fake3
import com.example.red30.data.fake4
import com.example.red30.data.fake5
import com.example.red30.data.fake6

class FakeConferenceRepository : ConferenceRepository {

    val favoriteIds: MutableList<Int> = mutableListOf()
    var throwLoadInfoException: Boolean = false
    var throwFavoritesException: Boolean = false

    override suspend fun loadConferenceInfo(): List<SessionInfo> {
        if (throwLoadInfoException) throw Exception()

        return listOf(
            SessionInfo.fake(),
            SessionInfo.fake2(),
            SessionInfo.fake3(),
            SessionInfo.fake4(),
            SessionInfo.fake5(),
            SessionInfo.fake6()
        )
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
