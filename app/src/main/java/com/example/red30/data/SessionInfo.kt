package com.example.red30.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SessionInfo(
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

@Serializable
data class Session(
    val id: Int,
    val speakerId: Int,
//    "start_time": "07/27/2020 02:00 PM",
//    "end_time": "07/27/2020 03:30 PM",
    val name: String,
    val description: String,
    val track: String,
    val roomName: String,
)
