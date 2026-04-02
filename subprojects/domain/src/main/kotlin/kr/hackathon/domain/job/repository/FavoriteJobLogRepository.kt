package kr.hackathon.domain.job.repository

import kr.hackathon.domain.job.entity.FavoriteJobLog
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteJobLogRepository : JpaRepository<FavoriteJobLog, Long>
