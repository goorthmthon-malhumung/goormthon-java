package kr.hackathon.service.experience.response

import kr.hackathon.domain.experience.entity.BookingStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class ExperienceBookingResponse(
    val id: Long,
    val experienceId: Long,
    val userId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: BookingStatus,
    val createdAt: LocalDateTime,
)
