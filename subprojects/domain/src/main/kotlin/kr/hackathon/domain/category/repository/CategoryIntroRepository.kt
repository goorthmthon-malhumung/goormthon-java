package kr.hackathon.domain.category.repository

import kr.hackathon.domain.category.entity.CategoryIntro
import kr.hackathon.domain.category.entity.CategoryType
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryIntroRepository : JpaRepository<CategoryIntro, Long> {
    fun findByCategoryTypeAndCategoryName(categoryType: CategoryType, categoryName: String): CategoryIntro?
}
