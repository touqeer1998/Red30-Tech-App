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
    val day: Day = Day.Day1,
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
    name = "AI for Beginners: Very Easy Path",
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

fun Session.Companion.fake3() = Session(
    id = 3,
    speakerId = 3,
    name = "Hacking for Good",
    startTime = "02:00 PM",
    endTime = "03:30 PM",
    description = """
        #Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
        #incididunt ut labore et dolore magna aliqua. 
            
        #Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip 
        #ex ea commodo consequat.
        """.trimMargin("#"),
    track = "Hacks",
    roomName = "Room 104"
)

fun Speaker.Companion.fake() = Speaker(
    id = 1,
    name = "Alycia Jones",
    title = "VP of Engineering",
    imageUrl = "https://i.pravatar.cc/150?img=47",
    bio = """Alycia is a seasoned engineer with a passion for building scalable and reliable systems. 
        |She's also an advocate for diversity and inclusion in tech.""".trimMargin(),
    organization = "Binaryville"
)

fun Speaker.Companion.fake2() = Speaker(
    id = 2,
    name = "Jamil Waters",
    title = "Research Scientist",
    bio = "Jamil is a research scientist specializing in artificial intelligence and machine learning.",
    organization = "SAMOCA"
)

fun Speaker.Companion.fake3() = Speaker(
    id = 3,
    name = "Bianca Adams",
    title = "Security Engineer",
    imageUrl = "https://i.pravatar.cc/150?img=47",
    bio = """Bianca is a seasoned security engineer with a passion for protecting digital assets. 
        |With extensive experience in cybersecurity.""".trimMargin(),
    organization = "SecureSphere"
)

fun Speaker.Companion.fake4() = Speaker(
    id = 4,
    name = "Carlos Mendez",
    title = "Frontend Engineer",
    bio = """Carlos is a frontend engineer who excels at creating user-friendly interfaces. 
        |He loves to work with the latest frameworks.""".trimMargin(),
    organization = "FrontendMasters"
)

fun Speaker.Companion.fake5() = Speaker(
    id = 5,
    name = "Isabel Garcia",
    title = "Data Scientist",
    bio = """Isabel is a data scientist with a strong background in statistical modeling and 
        |machine learning. She enjoys uncovering insights from complex data sets.""".trimMargin(),
    organization = "Data Insights Co."
)

fun SessionInfo.Companion.fake() = SessionInfo(
    session = Session.fake(),
    speaker = Speaker.fake()
)

fun SessionInfo.Companion.fake2() = SessionInfo(
    session = Session.fake2(),
    speaker = Speaker.fake2(),
    day = Day.Day2
)

fun SessionInfo.Companion.fake3() = SessionInfo(
    session = Session.fake3(),
    speaker = Speaker.fake3()
)

fun SessionInfo.Companion.fake4() = SessionInfo(
    session = Session.fake().copy(id = 4),
    speaker = Speaker.fake4()
)

fun SessionInfo.Companion.fake5() = SessionInfo(
    session = Session.fake2().copy(id = 5),
    speaker = Speaker.fake5()
)

fun SessionInfo.Companion.fake6() = SessionInfo(
    session = Session.fake3().copy(id = 6),
    speaker = Speaker.fake2()
)
