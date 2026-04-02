package kr.hackathon.service.experience.service

import kr.hackathon.domain.experience.entity.ExperienceBooking
import kr.hackathon.domain.experience.repository.ExperienceBookingRepository
import kr.hackathon.domain.experience.repository.ExperienceDetailRepository
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.experience.request.CreateExperienceBookingRequest
import kr.hackathon.service.experience.response.ExperienceBookingResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ExperienceBookingService(
    private val experienceBookingRepository: ExperienceBookingRepository,
    private val experienceDetailRepository: ExperienceDetailRepository,
    private val memberRepository: MemberRepository,
) {
    fun createBooking(userId: Long, request: CreateExperienceBookingRequest): ExperienceBookingResponse {
        val user = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }
        val experience = experienceDetailRepository.findById(request.experienceId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 체험입니다.") }

        val booking = experienceBookingRepository.save(
            ExperienceBooking(
                experience = experience,
                user = user,
                startDate = request.startDate,
                endDate = request.endDate,
            )
        )
        return booking.toResponse()
    }

    private fun ExperienceBooking.toResponse() = ExperienceBookingResponse(
        id = id,
        experienceId = experience.id,
        userId = user.id,
        startDate = startDate,
        endDate = endDate,
        status = status,
        createdAt = createdAt,
    )
}
