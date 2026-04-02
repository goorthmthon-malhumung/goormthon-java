package kr.hackathon.service.job.response

import kr.hackathon.domain.job.entity.PhysicalLevel
import java.time.LocalDateTime

data class JobListResponse(
    val id: Long,
    val title: String,
    val jobType: String,
    val skills: List<String>,
    val workHours: String,
    val physicalLevel: PhysicalLevel,
    val mediaUrl: String?,
    val photoUrl: String?,
    val mainUrl: String?,
    val createdAt: LocalDateTime,
)

data class JobDetailResponse(
    val id: Long,
    val title: String,
    val jobType: String,
    val skills: List<String>,
    val workHours: String,
    val physicalLevel: PhysicalLevel,
    val mediaUrl: String?,
    val photoUrl: String?,
    val mainUrl: String?,
    val createdAt: LocalDateTime,
    val categoryIntroduction: String?,
)
