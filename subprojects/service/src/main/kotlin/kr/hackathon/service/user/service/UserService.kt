package kr.hackathon.service.user.service

import kr.hackathon.domain.user.entity.Member
import kr.hackathon.domain.user.repository.MemberRepository
import kr.hackathon.service.user.request.SignInRequest
import kr.hackathon.service.user.request.SignUpRequest
import kr.hackathon.service.user.response.MemberInfoResponse
import kr.hackathon.service.user.response.SignUpResponse
import kr.hackathon.service.user.session.SessionUser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: MemberRepository,
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
            ).apply {
                jobTitle = request.jobTitle
                workYear = request.experience
            }
        )

        return user.toSignUpResponse() to user.toSessionUser()
    }

    @Transactional(readOnly = true)
    fun getMemberInfo(memberId: Long): MemberInfoResponse {
        val member = userRepository.findById(memberId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 회원입니다.") }
        return MemberInfoResponse(
            id = member.id,
            name = member.name,
            isMentor = member.isMentor,
            jobTitle = member.jobTitle,
            workYear = member.workYear,
        )
    }

    fun signIn(request: SignInRequest): SessionUser {
        val user = userRepository.findByPhone(request.phone)
            ?: throw IllegalArgumentException("존재하지 않는 휴대폰 번호입니다.")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 올바르지 않습니다.")
        }

        return user.toSessionUser()
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
