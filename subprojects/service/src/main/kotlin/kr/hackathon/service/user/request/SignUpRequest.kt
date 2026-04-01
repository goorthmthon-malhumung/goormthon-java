package kr.hackathon.service.user.request

data class SignUpRequest(
    val name: String,
    val phone: String,
    val password: String,
    val isMentor: Boolean,
)
