package kr.hackathon.service.experience.request

import java.time.LocalDate

data class CreateExperienceBookingRequest(
    val experienceId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
