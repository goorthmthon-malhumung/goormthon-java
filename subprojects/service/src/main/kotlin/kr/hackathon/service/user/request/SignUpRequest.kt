package kr.hackathon.service.user.request

data class SignUpRequest(
    val name: String,
    val phone: String,
    val password: String,
    val isMentor: Boolean,
    val introduce: String = "",
    val interests: String = "",
    val workYear: Int = 0
)
