package kr.hackathon.service.job.request

import kr.hackathon.domain.job.entity.PhysicalLevel

data class CreateJobRequest(
    val title: String,
    val jobType: String,
    val skills: String,
    val workHours: String,
    val physicalLevel: PhysicalLevel,
)
