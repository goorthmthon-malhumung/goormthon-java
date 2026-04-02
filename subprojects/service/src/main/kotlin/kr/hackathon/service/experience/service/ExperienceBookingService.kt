package kr.hackathon.service.experience.service

import kr.hackathon.domain.experience.entity.ExperienceBooking
import kr.hackathon.domain.experience.repository.ExperienceBookingRepository
import java.time.LocalDate
import kr.hackathon.domain.experience.repository.ExperienceDetailRepository
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.experience.request.CreateExperienceBookingRequest
import kr.hackathon.service.experience.response.ExperienceBookingListResponse
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
    @Transactional(readOnly = true)
    fun getMyBookings(userId: Long): List<ExperienceBookingListResponse> {
        return experienceBookingRepository.findByUserIdAndStartDateAfter(userId, LocalDate.now()).map { it.toListResponse() }
    }

    @Transactional(readOnly = true)
    fun getMyCompletedBookings(userId: Long): List<ExperienceBookingListResponse> {
        return experienceBookingRepository.findByUserIdAndStartDateBefore(userId, LocalDate.now()).map { it.toListResponse() }
    }

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

    private fun ExperienceBooking.toListResponse() = ExperienceBookingListResponse(
        bookingId = id,
        experienceId = experience.id,
        hostName = experience.user.name,
        experienceType = experience.experienceType,
        experienceDate = experience.experienceDate,
        mainUrl = experience.mainUrl,
        startDate = startDate,
        endDate = endDate,
        status = status,
        createdAt = createdAt!!,
    )

    private fun ExperienceBooking.toResponse() = ExperienceBookingResponse(
        id = id,
        experienceId = experience.id,
        userId = user.id,
        startDate = startDate,
        endDate = endDate,
        status = status,
        createdAt = createdAt!!,
    )
}
