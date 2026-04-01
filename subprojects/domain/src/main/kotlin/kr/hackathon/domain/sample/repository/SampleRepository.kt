package kr.hackathon.domain.sample.repository

import kr.hackathon.domain.sample.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : JpaRepository<Sample, Long>
