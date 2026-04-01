package kr.hackathon.ui.api.controller.sample

import kr.hackathon.service.sample.request.SampleCreateRequest
import kr.hackathon.service.sample.service.SampleService
import kr.hackathon.ui.api.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/samples")
class SampleController(
    private val sampleService: SampleService,
) {
    @PostMapping
    fun create(@RequestBody request: SampleCreateRequest): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(sampleService.create(request)))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(sampleService.getById(id)))
    }
}
