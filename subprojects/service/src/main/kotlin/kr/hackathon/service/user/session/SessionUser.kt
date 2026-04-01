package kr.hackathon.service.user.session

import java.io.Serializable

data class SessionUser(
    val id: Long,
    val name: String,
    val phone: String,
    val isMentor: Boolean,
) : Serializable

const val SESSION_USER_KEY = "LOGIN_USER"
