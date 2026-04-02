package kr.hackathon.ui.api.controller.job

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import kr.hackathon.domain.job.entity.PhysicalLevel
import kr.hackathon.service.job.request.CreateJobRequest
import kr.hackathon.service.job.service.JobService
import kr.hackathon.service.user.session.SESSION_USER_KEY
import kr.hackathon.service.user.session.SessionUser
import kr.hackathon.ui.api.common.response.ApiResponse
import kr.hackathon.ui.api.common.response.ErrorResponse
import kr.hackathon.ui.api.storage.FileStorageService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Tag(name = "Job", description = "직업 API")
@RestController
@RequestMapping("/jobs")
class JobController(
    private val jobService: JobService,
    private val fileStorageService: FileStorageService,
) {
    @Operation(summary = "직업 목록 조회", description = "직업 목록을 페이징으로 조회합니다. 모든 필터는 옵션값입니다.")
    @GetMapping
    fun getJobs(
        @RequestParam(required = false) jobType: String?,
        @RequestParam(required = false) skills: String?,
        @RequestParam(required = false) workHours: String?,
        @RequestParam(required = false) physicalLevel: PhysicalLevel?,
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false) endDate: LocalDate?,
        @PageableDefault(size = 10, sort = ["createdAt"]) pageable: Pageable,
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(
            ApiResponse.success(jobService.getJobs(jobType, skills, workHours, physicalLevel, startDate, endDate, pageable))
        )
    }

    @Operation(summary = "직업 상세 조회", description = "ID로 직업 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    fun getJob(
        @PathVariable id: Long,
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(jobService.getJob(id)))
    }

    @Operation(summary = "직업 등록", description = "새로운 직업을 등록합니다. 로그인 세션이 필요합니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createJob(
        @RequestPart("data") request: CreateJobRequest,
        @RequestPart("photoUrl", required = false) photoFile: MultipartFile?,
        @RequestPart("mainUrl", required = false) mainFile: MultipartFile?,
        @RequestPart("mediaUrl", required = false) mediaFile: MultipartFile?,
        @RequestPart("mediaUrl2", required = false) mediaFile2: MultipartFile?,
        session: HttpSession,
    ): ResponseEntity<ApiResponse<*>> {
        val sessionUser = session.getAttribute(SESSION_USER_KEY) as? SessionUser
            ?: return ResponseEntity.status(401).body(ApiResponse.error(
                ErrorResponse("UNAUTHORIZED", "로그인이 필요합니다.")
            ))

        val photoUrl = photoFile?.let { fileStorageService.storePhoto(it) }
        val mainUrl = mainFile?.let { fileStorageService.storePhoto(it) }
        val mediaUrl = mediaFile?.let { fileStorageService.storeMedia(it) }
        val mediaUrl2 = mediaFile2?.let { fileStorageService.storeMedia(it) }

        return ResponseEntity.ok(ApiResponse.success(
            jobService.createJob(sessionUser.id, request, photoUrl, mainUrl, mediaUrl, mediaUrl2)
        ))
    }
}
