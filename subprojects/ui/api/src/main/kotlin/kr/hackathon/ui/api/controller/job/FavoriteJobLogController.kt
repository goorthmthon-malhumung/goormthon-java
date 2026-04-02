package kr.hackathon.ui.api.controller.job

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hackathon.service.job.service.FavoriteJobLogService
import kr.hackathon.ui.api.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Job", description = "직업 API")
@RestController
@RequestMapping("/jobs")
class FavoriteJobLogController(
    private val favoriteJobLogService: FavoriteJobLogService,
) {
    @Operation(summary = "관심 직업 등록", description = "사용자 ID와 직업 ID로 관심 직업을 등록합니다.")
    @PostMapping("/{jobId}/favorite")
    fun addFavorite(
        @PathVariable jobId: Long,
        @RequestParam userId: Long,
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(favoriteJobLogService.addFavorite(userId, jobId)))
    }
}
