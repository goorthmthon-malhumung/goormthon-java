package kr.hackathon.domain.category.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "category_intro")
class CategoryIntro(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val categoryType: CategoryType,

    @Column(nullable = false, length = 100)
    val categoryName: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val introduction: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}

enum class CategoryType {
    EXPERIENCE, JOB
}
