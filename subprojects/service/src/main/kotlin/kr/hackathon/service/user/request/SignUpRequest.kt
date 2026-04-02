package kr.hackathon.service.user.request

data class SignUpRequest(
    val name: String,
    val phone: String,
    val password: String,
    val isMentor: Boolean,
    val jobTitle: String = "멘티",
    val experience: Int = 0,
    val introduce: String = ""
)
