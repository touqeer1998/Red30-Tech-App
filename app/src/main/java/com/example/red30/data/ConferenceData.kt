package com.example.red30.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConferenceData(
    val speakers: List<Speaker>,
    val sessions: List<Session>,
)

@Serializable
data class Speaker(
    val id: Int,
    val name: String,
    val title: String,
    val organization: String,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val bio: String,
)

val Speaker.initial
    get() = name.first().uppercase()

@Serializable
data class Session(
    val id: Int,
    @SerialName("speaker_id")
    val speakerId: Int,
//    "start_time": "02:00 PM",
//    "end_time": "03:30 PM",
    @SerialName("session_name")
    val name: String,
    @SerialName("session_description")
    val description: String = "",
    @SerialName("session_track")
    val track: String,
    @SerialName("room_name")
    val roomName: String,
    val day: Day = Day.Day1
)

@Serializable
data class SessionInfo(
    val session: Session,
    val speaker: Speaker,
    val day: Day,
    val isFavorite: Boolean = false
)

@Serializable
enum class Day {
    @SerialName("day1") Day1,
    @SerialName("day2") Day2
}
