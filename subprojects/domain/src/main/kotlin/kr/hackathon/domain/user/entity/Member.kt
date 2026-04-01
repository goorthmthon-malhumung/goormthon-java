package kr.hackathon.domain.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Column(nullable = false, unique = true)
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
}