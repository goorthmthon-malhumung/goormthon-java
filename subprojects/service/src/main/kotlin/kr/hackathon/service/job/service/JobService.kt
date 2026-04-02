package kr.hackathon.service.job.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.hackathon.domain.category.entity.CategoryType
import kr.hackathon.domain.category.repository.CategoryIntroRepository
import kr.hackathon.domain.job.entity.Job
import kr.hackathon.domain.job.entity.PhysicalLevel
import kr.hackathon.domain.job.repository.JobRepository
import kr.hackathon.domain.job.repository.JobSpecification
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.job.request.CreateJobRequest
import kr.hackathon.service.job.response.JobDetailResponse
import kr.hackathon.service.job.response.JobListResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val objectMapper = jacksonObjectMapper()
private fun String.toStringList(): List<String> = objectMapper.readValue(this)

@Service
@Transactional
class JobService(
    private val jobRepository: JobRepository,
    private val memberRepository: MemberRepository,
    private val categoryIntroRepository: CategoryIntroRepository,
) {
    @Transactional(readOnly = true)
    fun getJobs(
        jobType: String?,
        skills: String?,
        workHours: String?,
        physicalLevel: PhysicalLevel?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        pageable: Pageable,
    ): Page<JobListResponse> {
        val spec = JobSpecification.filter(jobType, skills, workHours, physicalLevel, startDate, endDate)
        return jobRepository.findAll(spec, pageable).map { it.toListResponse() }
    }

    @Transactional(readOnly = true)
    fun getJob(id: Long): JobDetailResponse {
        val job = jobRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 직업입니다.") }
        return job.toDetailResponse()
    }

    fun createJob(
        userId: Long,
        request: CreateJobRequest,
        photoUrl: String?,
        mainUrl: String?,
        mediaUrl: String?,
        mediaUrl2: String?,
    ): JobDetailResponse {
        val user = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }

        val job = jobRepository.save(
            Job(
                user = user,
                title = request.title,
                jobType = request.jobType,
                skills = request.skills,
                workHours = request.workHours,
                physicalLevel = request.physicalLevel,
                photoUrl = photoUrl,
                mainUrl = mainUrl,
                mediaUrl = mediaUrl,
                mediaUrl2 = mediaUrl2,
            )
        )
        return job.toDetailResponse()
    }

    private fun Job.toListResponse() = JobListResponse(
        id = id,
        title = title,
        jobType = jobType,
        skills = skills.toStringList(),
        workHours = workHours,
        physicalLevel = physicalLevel,
        mediaUrl = mediaUrl,
        photoUrl = photoUrl,
        mainUrl = mainUrl,
        createdAt = createdAt,
    )

    private fun Job.toDetailResponse(): JobDetailResponse {
        val categoryIntroduction = categoryIntroRepository
            .findByCategoryTypeAndCategoryName(CategoryType.JOB, jobType)
            ?.introduction
        return JobDetailResponse(
            id = id,
            title = title,
            jobType = jobType,
            skills = skills.toStringList(),
            workHours = workHours,
            physicalLevel = physicalLevel,
            mediaUrl = mediaUrl,
            photoUrl = photoUrl,
            mainUrl = mainUrl,
            createdAt = createdAt,
            categoryIntroduction = categoryIntroduction,
        )
    }
}
