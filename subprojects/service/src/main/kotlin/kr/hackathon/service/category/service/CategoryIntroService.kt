package kr.hackathon.service.category.service

import kr.hackathon.domain.category.entity.CategoryType
import kr.hackathon.domain.category.repository.CategoryIntroRepository
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.category.response.CategoryIntroResponse
import kr.hackathon.service.common.exception.BaseException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryIntroService(
    private val categoryIntroRepository: CategoryIntroRepository,
    private val memberRepository: MemberRepository,
) {
    fun getCategoryIntro(categoryType: CategoryType, categoryName: String, memberId: Long): CategoryIntroResponse {
        val member = memberRepository.findById(memberId).orElseThrow {
            BaseException("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다.")
        }
        val categoryIntro = categoryIntroRepository.findByCategoryTypeAndCategoryName(categoryType, categoryName)
            ?: throw BaseException("CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다.")

        val introduction = categoryIntro.introduction
            .replace("\${name}", member.name)
            .replace("\${experience}", "${member.workYear}년")

        return CategoryIntroResponse(
            categoryType = categoryIntro.categoryType.name,
            categoryName = categoryIntro.categoryName,
            introduction = introduction,
        )
    }
}
