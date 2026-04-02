package kr.hackathon.service.experience.response

import kr.hackathon.domain.experience.entity.ExperienceType
import java.time.LocalDate
import java.time.LocalTime

data class ExperienceDetailResponse(
    val id: Long,
    val experienceType: ExperienceType,
    val experienceDate: LocalDate,
    val experienceTime: LocalTime,
    val location: String,
    val maxParticipants: Int,
    val introduction: String,
    val schedule: String,
    val inclusions: List<String>?,
    val requirements: List<String>?,
    val mainUrl: String?,
    val photoUrl: String?,
    val photoUrl2: String?,
    val mediaUrl: String?,
    val mediaUrl2: String?,
    val jobId: Long?,
)
