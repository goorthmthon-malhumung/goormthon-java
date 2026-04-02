package kr.hackathon.domain.experience.repository

import kr.hackathon.domain.experience.entity.ExperienceBooking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ExperienceBookingRepository : JpaRepository<ExperienceBooking, Long> {
    @Query("SELECT b FROM ExperienceBooking b JOIN FETCH b.experience e JOIN FETCH e.user WHERE b.user.id = :userId AND b.startDate >= :today")
    fun findByUserIdAndStartDateAfter(userId: Long, today: LocalDate): List<ExperienceBooking>

    @Query("SELECT b FROM ExperienceBooking b JOIN FETCH b.experience e JOIN FETCH e.user WHERE b.user.id = :userId AND b.startDate < :today")
    fun findByUserIdAndStartDateBefore(userId: Long, today: LocalDate): List<ExperienceBooking>
}
