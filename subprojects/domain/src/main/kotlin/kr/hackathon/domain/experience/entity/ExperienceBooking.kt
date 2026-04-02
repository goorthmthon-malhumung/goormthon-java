package kr.hackathon.domain.experience.entity

import jakarta.persistence.*
import kr.hackathon.domain.user.entity.Member
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@NoArgsConstructor
@Table(name = "experience_booking")
class ExperienceBooking(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", nullable = false)
    val experience: ExperienceDetail,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Member,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = false)
    val endDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: BookingStatus = BookingStatus.PENDING,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}

enum class BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}
