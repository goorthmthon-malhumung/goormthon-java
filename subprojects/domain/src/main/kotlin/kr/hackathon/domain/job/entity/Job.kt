package kr.hackathon.domain.job.entity

import jakarta.persistence.*
import kr.hackathon.domain.user.entity.Member
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "job")
class Job(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Member,

    @Column(nullable = false, length = 200)
    val title: String,

    @Column(nullable = false, length = 100)
    val jobType: String,

    @Column(nullable = false, columnDefinition = "JSON")
    val skills: String,

    @Column(nullable = false, length = 100)
    val workHours: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val physicalLevel: PhysicalLevel,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(length = 500)
    var photoUrl: String? = null

    @Column(length = 500)
    var photo2Url: String? = null

    @Column(length = 500)
    var mainUrl: String? = null

    @Column(length = 500)
    var mediaUrl: String? = null

    @Column(length = 500)
    var mediaUrl2: String? = null
}

enum class PhysicalLevel {
    LOW, MEDIUM, HIGH
}
