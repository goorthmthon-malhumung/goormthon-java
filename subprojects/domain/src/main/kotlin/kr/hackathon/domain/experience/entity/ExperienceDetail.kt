package kr.hackathon.domain.experience.entity

import jakarta.persistence.*
import kr.hackathon.domain.user.entity.Member
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@NoArgsConstructor
@Table(name = "experience_detail")
class ExperienceDetail(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Member,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val experienceType: ExperienceType,

    @Column(nullable = false)
    val experienceDate: LocalDate,

    @Column(nullable = false)
    val experienceTime: LocalTime,

    @Column(nullable = false, length = 300)
    val location: String,

    @Column(nullable = false)
    val maxParticipants: Int = 10,

    @Column(nullable = false, columnDefinition = "TEXT")
    val introduction: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val schedule: String,

    @Column(columnDefinition = "JSON")
    val inclusions: String? = null,

    @Column(columnDefinition = "JSON")
    val requirements: String? = null,

    @Column(nullable = false)
    val price: Long,

    @Column(columnDefinition = "JSON")
    val skills: String? = null,

    @Column
    val jobId: Long? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @Column(length = 500)
    var photoUrl: String? = null

    @Column(length = 500)
    var photoUrl2: String? = null

    @Column(length = 500)
    var mediaUrl: String? = null

    @Column(length = 500)
    var mediaUrl2: String? = null

    @Column(length = 500)
    var mainUrl: String? = null
}

enum class ExperienceType {
    HAENYEO, DOLDAM, GAMGYUL, MOKJANG
}
