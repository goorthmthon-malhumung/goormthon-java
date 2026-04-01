package kr.hackathon.ui.api.common.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ErrorResponse?,
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(success = true, data = data, error = null)

        fun error(error: ErrorResponse): ApiResponse<Nothing> =
            ApiResponse(success = false, data = null, error = error)
    }
}

data class ErrorResponse(
    val code: String,
    val message: String,
)
