package com.example.red30.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
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

val Session.duration: Long
    get() {
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val startTime = LocalTime.parse(startTime, timeFormatter)
        val endTime = LocalTime.parse(endTime, timeFormatter)

        println("$startTime, $endTime")

        val duration = Duration.between(startTime, endTime)
        return duration.toMinutes()
    }

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

fun Session.Companion.fake() = Session(
    id = 1,
    speakerId = 1,
    name = "AI for Beginners",
    startTime = "02:00 PM",
    endTime = "03:00 PM",
    description = """
        #Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
        #incididunt ut labore et dolore magna aliqua. 
            
        #Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip 
        #ex ea commodo consequat.
        """.trimMargin("#"),
    track = "Artificial Intelligence",
    roomName = "Room 201"
)

fun Session.Companion.fake2() = Session(
    id = 2,
    speakerId = 2,
    name = "Not a silver bullet",
    startTime = "09:00 AM",
    endTime = "09:30 AM",
    description = """
        #Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
        #incididunt ut labore et dolore magna aliqua. 
            
        #Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip 
        #ex ea commodo consequat.
        """.trimMargin("#"),
    track = "Artificial Intelligence",
    roomName = "Room 104"
)

fun Speaker.Companion.fake() = Speaker(
    id = 1,
    name = "Alycia Jones",
    title = "VP of Engineering",
    bio = "She's a superstar!",
    organization = "Binaryville"
)

fun Speaker.Companion.fake2() = Speaker(
    id = 2,
    name = "Jamil Waters",
    title = "Research Scientist",
    bio = "A real artist and a fake one.",
    organization = "SAMOCA"
)

fun SessionInfo.Companion.fake() = SessionInfo(
    session = Session.fake(),
    speaker = Speaker.fake(),
    day = Day.Day1
)

fun SessionInfo.Companion.fake2() = SessionInfo(
    session = Session.fake2(),
    speaker = Speaker.fake2(),
    day = Day.Day2
)
