package kr.hackathon.domain.experience.repository

import kr.hackathon.domain.experience.entity.ExperienceBooking
import org.springframework.data.jpa.repository.JpaRepository

interface ExperienceBookingRepository : JpaRepository<ExperienceBooking, Long>
