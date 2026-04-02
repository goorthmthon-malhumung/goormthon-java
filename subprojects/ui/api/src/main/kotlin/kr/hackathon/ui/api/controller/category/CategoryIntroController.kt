package kr.hackathon.ui.api.controller.category

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpSession
import kr.hackathon.domain.category.entity.CategoryType
import kr.hackathon.service.category.service.CategoryIntroService
import kr.hackathon.service.user.session.SESSION_USER_KEY
import kr.hackathon.service.user.session.SessionUser
import kr.hackathon.ui.api.common.response.ApiResponse
import kr.hackathon.ui.api.common.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CategoryIntro", description = "카테고리 소개 API")
@RestController
@RequestMapping("/category-intros")
class CategoryIntroController(
    private val categoryIntroService: CategoryIntroService,
) {
    @Operation(summary = "카테고리 소개 조회", description = "category_type과 category_name으로 소개글을 조회합니다. 로그인 세션의 사용자 정보로 \${name}, \${experience}가 치환됩니다.")
    @GetMapping
    fun getCategoryIntro(
        @RequestParam categoryType: CategoryType,
        @RequestParam categoryName: String,
        session: HttpSession,
    ): ResponseEntity<ApiResponse<*>> {
        val sessionUser = session.getAttribute(SESSION_USER_KEY) as? SessionUser
            ?: return ResponseEntity.status(401).body(ApiResponse.error(
                ErrorResponse("UNAUTHORIZED", "로그인이 필요합니다.")
            ))

        return ResponseEntity.ok(
            ApiResponse.success(
                categoryIntroService.getCategoryIntro(categoryType, categoryName, sessionUser.id)
            )
        )
    }
}
