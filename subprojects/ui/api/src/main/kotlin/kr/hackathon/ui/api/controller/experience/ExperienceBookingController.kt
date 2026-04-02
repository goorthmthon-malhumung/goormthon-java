package kr.hackathon.ui.api.controller.experience

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import kr.hackathon.service.experience.request.CreateExperienceBookingRequest
import kr.hackathon.service.experience.service.ExperienceBookingService
import kr.hackathon.service.user.session.SESSION_USER_KEY
import kr.hackathon.service.user.session.SessionUser
import kr.hackathon.ui.api.common.response.ApiResponse
import kr.hackathon.ui.api.common.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Experience", description = "체험 API")
@RestController
@RequestMapping("/experiences/bookings")
class ExperienceBookingController(
    private val experienceBookingService: ExperienceBookingService,
) {
    @Operation(summary = "체험 예약", description = "체험을 예약합니다. 로그인 세션이 필요합니다.")
    @PostMapping
    fun createBooking(
        @RequestBody request: CreateExperienceBookingRequest,
        session: HttpSession,
    ): ResponseEntity<ApiResponse<*>> {
        val sessionUser = session.getAttribute(SESSION_USER_KEY) as? SessionUser
            ?: return ResponseEntity.status(401).body(ApiResponse.error(
                ErrorResponse("UNAUTHORIZED", "로그인이 필요합니다.")
            ))
        return ResponseEntity.ok(ApiResponse.success(
            experienceBookingService.createBooking(sessionUser.id, request)
        ))
    }
}
