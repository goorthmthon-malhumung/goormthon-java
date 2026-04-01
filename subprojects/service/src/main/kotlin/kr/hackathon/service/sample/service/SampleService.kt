package kr.hackathon.service.sample.service

import kr.hackathon.domain.sample.entity.Sample
import kr.hackathon.domain.sample.repository.SampleRepository
import kr.hackathon.service.sample.request.SampleCreateRequest
import kr.hackathon.service.sample.response.SampleResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SampleService(
    private val sampleRepository: SampleRepository,
) {
    fun create(request: SampleCreateRequest): SampleResponse {
        val sample = sampleRepository.save(Sample(name = request.name))
        return sample.toResponse()
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): SampleResponse {
        val sample = sampleRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Sample not found: $id")
        return sample.toResponse()
    }

    private fun Sample.toResponse() = SampleResponse(
        id = id!!,
        name = name,
    )
}
