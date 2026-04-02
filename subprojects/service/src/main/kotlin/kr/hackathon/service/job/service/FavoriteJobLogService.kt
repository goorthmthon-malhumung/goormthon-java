package kr.hackathon.service.job.service

import kr.hackathon.domain.job.entity.FavoriteJobLog
import kr.hackathon.domain.job.repository.FavoriteJobLogRepository
import kr.hackathon.domain.job.repository.JobRepository
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.job.response.FavoriteJobLogResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FavoriteJobLogService(
    private val favoriteJobLogRepository: FavoriteJobLogRepository,
    private val memberRepository: MemberRepository,
    private val jobRepository: JobRepository,
) {
    fun addFavorite(userId: Long, jobId: Long): FavoriteJobLogResponse {
        val user = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }
        val job = jobRepository.findById(jobId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 직업입니다.") }

        val log = favoriteJobLogRepository.save(FavoriteJobLog(user = user, job = job))
        return log.toResponse()
    }

    private fun FavoriteJobLog.toResponse() = FavoriteJobLogResponse(
        id = id,
        userId = user.id,
        jobId = job.id,
        createdAt = createdAt!!,
    )
}
