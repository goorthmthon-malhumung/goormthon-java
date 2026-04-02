package kr.hackathon.service.experience.request

import kr.hackathon.domain.experience.entity.ExperienceType
import java.time.LocalDate
import java.time.LocalTime

data class CreateExperienceDetailRequest(
    val experienceType: ExperienceType,
    val experienceDate: LocalDate,
    val experienceTime: LocalTime,
    val location: String,
    val maxParticipants: Int = 10,
    val introduction: String,
    val schedule: String,
    val inclusions: List<String>? = null,
    val requirements: List<String>? = null,
    val price: Long,
    val jobId: Long? = null,
)
