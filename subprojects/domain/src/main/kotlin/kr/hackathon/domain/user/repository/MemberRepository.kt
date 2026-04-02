package kr.hackathon.domain.user.repository

import kr.hackathon.domain.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByPhone(phone: String): Boolean
    fun findByPhone(phone: String): Member?
}
