package kr.hackathon.service.common.exception

open class BaseException(
    val code: String,
    message: String,
) : RuntimeException(message)
