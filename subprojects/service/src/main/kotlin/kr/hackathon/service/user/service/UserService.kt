package kr.hackathon.service.user.service

import kr.hackathon.domain.user.entity.Member
import kr.hackathon.domain.user.repository.UserRepository
import kr.hackathon.service.user.request.SignUpRequest
import kr.hackathon.service.user.response.SignUpResponse
import kr.hackathon.service.user.session.SessionUser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun signUp(request: SignUpRequest): Pair<SignUpResponse, SessionUser> {
        if (userRepository.existsByPhone(request.phone)) {
            throw IllegalArgumentException("이미 가입된 휴대폰 번호입니다.")
        }

        val user = userRepository.save(
            Member(
                name = request.name,
                phone = request.phone,
                password = passwordEncoder.encode(request.password),
                isMentor = request.isMentor,
            )
        )

        return user.toSignUpResponse() to user.toSessionUser()
    }

    private fun Member.toSignUpResponse() = SignUpResponse(
        name = name,
        phone = phone,
        isMentor = isMentor,
    )

    private fun Member.toSessionUser() = SessionUser(
        id = id!!,
        name = name,
        phone = phone,
        isMentor = isMentor,
    )
}
