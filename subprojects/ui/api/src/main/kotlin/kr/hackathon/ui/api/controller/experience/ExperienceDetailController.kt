package kr.hackathon.ui.api.controller.experience

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import kr.hackathon.service.experience.request.CreateExperienceDetailRequest
import kr.hackathon.service.experience.service.ExperienceDetailService
import kr.hackathon.service.user.session.SESSION_USER_KEY
import kr.hackathon.service.user.session.SessionUser
import kr.hackathon.ui.api.common.response.ApiResponse
import kr.hackathon.ui.api.common.response.ErrorResponse
import kr.hackathon.ui.api.storage.FileStorageService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Experience", description = "체험 API")
@RestController
@RequestMapping("/experiences")
class ExperienceDetailController(
    private val experienceDetailService: ExperienceDetailService,
    private val fileStorageService: FileStorageService,
) {
    @Operation(summary = "체험 상세 조회", description = "ID로 체험 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    fun getExperienceDetail(
        @PathVariable id: Long,
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(experienceDetailService.getExperienceDetail(id)))
    }

    @Operation(summary = "체험 등록", description = "새로운 체험을 등록합니다. 로그인 세션이 필요합니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createExperienceDetail(
        @RequestPart("data") request: CreateExperienceDetailRequest,
        @RequestPart("photoUrl", required = false) photoFile: MultipartFile?,
        @RequestPart("photoUrl2", required = false) photoFile2: MultipartFile?,
        @RequestPart("mediaUrl", required = false) mediaFile: MultipartFile?,
        @RequestPart("mediaUrl2", required = false) mediaFile2: MultipartFile?,
        session: HttpSession,
    ): ResponseEntity<ApiResponse<*>> {
        val sessionUser = session.getAttribute(SESSION_USER_KEY) as? SessionUser
            ?: return ResponseEntity.status(401).body(ApiResponse.error(
                ErrorResponse("UNAUTHORIZED", "로그인이 필요합니다.")
            ))

        val photoUrl = photoFile?.let { fileStorageService.storePhoto(it) }
        val photoUrl2 = photoFile2?.let { fileStorageService.storePhoto(it) }
        val mediaUrl = mediaFile?.let { fileStorageService.storePhoto(it) }
        val mediaUrl2 = mediaFile2?.let { fileStorageService.storePhoto(it) }

        return ResponseEntity.ok(ApiResponse.success(
            experienceDetailService.createExperienceDetail(sessionUser.id, request, photoUrl, photoUrl2, mediaUrl, mediaUrl2)
        ))
    }
}
