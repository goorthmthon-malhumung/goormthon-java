package kr.hackathon.domain.user.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@NoArgsConstructor
@Table(name = "member")
class Member(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val phone: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val isMentor: Boolean = false,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var jobTitle: String = "멘티"

    var workYear: Int = 0

    @CreatedDate
    var createdAt: LocalDateTime? = null
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}