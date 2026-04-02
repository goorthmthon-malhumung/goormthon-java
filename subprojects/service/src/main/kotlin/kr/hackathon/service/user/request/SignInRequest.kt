package kr.hackathon.service.user.request

data class SignInRequest(
    val phone: String,
    val password: String,
)
