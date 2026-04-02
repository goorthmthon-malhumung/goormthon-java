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

    @Column(length = 500)
    val photoUrl: String? = null,

    @Column(length = 500)
    val mainUrl: String? = null,

    @Column(length = 500)
    val mediaUrl: String? = null,

    @Column(length = 500)
    val mediaUrl2: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}

enum class PhysicalLevel {
    LOW, MEDIUM, HIGH
}
