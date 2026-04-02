package kr.hackathon.domain.experience.repository

import kr.hackathon.domain.experience.entity.ExperienceDetail
import org.springframework.data.jpa.repository.JpaRepository

interface ExperienceDetailRepository : JpaRepository<ExperienceDetail, Long>
