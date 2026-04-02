package kr.hackathon.service.user.response

data class MemberInfoResponse(
    val id: Long,
    val name: String,
    val isMentor: Boolean,
    val introduce: String,
)
