package kr.hackathon.ui.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import kr.hackathon.service.user.request.SignUpRequest
import kr.hackathon.service.user.service.UserService
import kr.hackathon.service.user.session.SESSION_USER_KEY
import kr.hackathon.service.user.session.SessionUser
import kr.hackathon.ui.api.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "회원 API")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @Operation(summary = "회원가입", description = "회원가입 후 세션에 로그인 정보를 저장합니다.")
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest,
        session: HttpSession,
    ): ResponseEntity<ApiResponse<*>> {
        val (response, sessionUser) = userService.signUp(request)
        session.setAttribute(SESSION_USER_KEY, sessionUser)
        session.maxInactiveInterval = -1
        return ResponseEntity.ok(ApiResponse.success(response))
    }


    @Operation(summary = "로그인 여부 확인", description = "세션이 존재하면 로그인된 사용자 정보를 반환합니다. 세션이 없으면 로그인되지 않은 상태입니다.")
    @GetMapping("/me")
    fun me(session: HttpSession): ResponseEntity<ApiResponse<*>> {
        val sessionUser = session.getAttribute(SESSION_USER_KEY) as? SessionUser
            ?: return ResponseEntity.ok(ApiResponse.success(null))
        return ResponseEntity.ok(ApiResponse.success(sessionUser))
    }

    @Operation(summary = "로그아웃", description = "세션을 삭제하고 로그아웃합니다.")
    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<ApiResponse<*>> {
        session.invalidate()
        return ResponseEntity.ok(ApiResponse.success(null))
    }
}
