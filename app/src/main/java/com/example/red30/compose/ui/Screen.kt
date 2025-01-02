package com.example.red30.compose.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.red30.R

sealed class Screen(
    val route: String,
    @StringRes val labelResourceId: Int,
    val icon: ImageVector
) {
    data object Sessions: Screen("sessions", R.string.sessions_label, Icons.Filled.DateRange)
    data object Speakers: Screen("speakers", R.string.speakers_label, Icons.Filled.Person)
    data object Favorites: Screen("favorites", R.string.favorites_label, Icons.Outlined.FavoriteBorder)

    // this is a nested screen
    data object SessionDetail: Screen("sessionDetail", 0, Icons.Filled.DateRange)
    data object SpeakerDetail: Screen("speakerDetail", 0, Icons.Filled.Person)

    companion object {
        fun valueOf(route: String): Screen = when(route) {
            "sessions" -> Sessions
            "speakers" -> Speakers
            "favorites" -> Favorites
            "sessionDetail" -> SessionDetail
            "speakerDetail" -> SpeakerDetail
            else -> Sessions
        }
    }
}

val topLevelScreens = listOf(Screen.Sessions, Screen.Speakers, Screen.Favorites)
