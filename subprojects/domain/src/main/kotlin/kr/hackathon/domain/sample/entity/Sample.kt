package kr.hackathon.domain.sample.entity

import jakarta.persistence.*

@Entity
@Table(name = "sample")
class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,
)
