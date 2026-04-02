package kr.hackathon.service.job.response

import java.time.LocalDateTime

data class FavoriteJobLogResponse(
    val id: Long,
    val userId: Long,
    val jobId: Long,
    val createdAt: LocalDateTime,
)
