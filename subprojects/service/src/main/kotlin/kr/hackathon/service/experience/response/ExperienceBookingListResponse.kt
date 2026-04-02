package kr.hackathon.service.experience.response

import kr.hackathon.domain.experience.entity.BookingStatus
import kr.hackathon.domain.experience.entity.ExperienceType
import java.time.LocalDate
import java.time.LocalDateTime

data class ExperienceBookingListResponse(
    val bookingId: Long,
    val experienceId: Long,
    val hostName: String,
    val experienceType: ExperienceType,
    val experienceDate: LocalDate,
    val mainUrl: String?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: BookingStatus,
    val createdAt: LocalDateTime,
)
