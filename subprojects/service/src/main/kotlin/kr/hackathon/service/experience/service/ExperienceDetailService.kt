package kr.hackathon.service.experience.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.hackathon.domain.category.entity.CategoryType
import kr.hackathon.domain.category.repository.CategoryIntroRepository
import kr.hackathon.domain.experience.entity.ExperienceDetail
import kr.hackathon.domain.experience.repository.ExperienceDetailRepository
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.experience.request.CreateExperienceDetailRequest
import kr.hackathon.service.experience.response.ExperienceDetailResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val objectMapper = jacksonObjectMapper()
private fun String?.toStringList(): List<String>? = this?.let { objectMapper.readValue(it) }
private fun List<String>?.toJson(): String? = this?.let { objectMapper.writeValueAsString(it) }

@Service
@Transactional(readOnly = true)
class ExperienceDetailService(
    private val experienceDetailRepository: ExperienceDetailRepository,
    private val memberRepository: MemberRepository,
    private val categoryIntroRepository: CategoryIntroRepository,
) {
    fun getExperienceDetail(id: Long): ExperienceDetailResponse {
        val experience = experienceDetailRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 체험입니다.") }
        return experience.toResponse()
    }

    @Transactional
    fun createExperienceDetail(
        userId: Long,
        request: CreateExperienceDetailRequest,
        photoUrl: String?,
        photoUrl2: String?,
    ): ExperienceDetailResponse {
        val user = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }

        val experience = experienceDetailRepository.save(
            ExperienceDetail(
                user = user,
                experienceType = request.experienceType,
                experienceDate = request.experienceDate,
                experienceTime = request.experienceTime,
                location = request.location,
                maxParticipants = request.maxParticipants,
                introduction = request.introduction,
                schedule = request.schedule,
                inclusions = request.inclusions.toJson(),
                requirements = request.requirements.toJson(),
                price = request.price,
                jobId = request.jobId,
            ).apply {
                this.photoUrl = photoUrl
                this.photoUrl2 = photoUrl2
            }
        )
        return experience.toResponse()
    }

    private fun ExperienceDetail.toResponse(): ExperienceDetailResponse {
        val categoryIntroduction = categoryIntroRepository
            .findByCategoryTypeAndCategoryName(CategoryType.EXPERIENCE, experienceType.name)
            ?.introduction
        return ExperienceDetailResponse(
            id = id,
            experienceType = experienceType,
            experienceDate = experienceDate,
            experienceTime = experienceTime,
            location = location,
            maxParticipants = maxParticipants,
            introduction = introduction,
            schedule = schedule,
            inclusions = inclusions.toStringList(),
            requirements = requirements.toStringList(),
            photoUrl = photoUrl,
            photoUrl2 = photoUrl2,
            jobId = jobId,
            categoryIntroduction = categoryIntroduction,
        )
    }
}
