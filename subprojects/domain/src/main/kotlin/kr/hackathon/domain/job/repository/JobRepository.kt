package kr.hackathon.domain.job.repository

import kr.hackathon.domain.job.entity.Job
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface JobRepository : JpaRepository<Job, Long>, JpaSpecificationExecutor<Job>
