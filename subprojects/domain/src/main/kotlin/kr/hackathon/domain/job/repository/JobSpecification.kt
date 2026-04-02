package kr.hackathon.domain.job.repository

import jakarta.persistence.criteria.Predicate
import kr.hackathon.domain.job.entity.Job
import kr.hackathon.domain.job.entity.PhysicalLevel
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object JobSpecification {
    fun filter(
        jobType: String?,
        skills: String?,
        workHours: String?,
        physicalLevel: PhysicalLevel?,
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): Specification<Job> = Specification { root, _, cb ->
        val predicates = mutableListOf<Predicate>()

        jobType?.let { predicates.add(cb.equal(root.get<String>("jobType"), it)) }
        skills?.let { predicates.add(cb.like(root.get("skills"), "%$it%")) }
        workHours?.let { predicates.add(cb.like(root.get("workHours"), "%$it%")) }
        physicalLevel?.let { predicates.add(cb.equal(root.get<PhysicalLevel>("physicalLevel"), it)) }
        startDate?.let { predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), it.atStartOfDay())) }
        endDate?.let { predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), it.plusDays(1).atStartOfDay())) }

        cb.and(*predicates.toTypedArray())
    }
}
