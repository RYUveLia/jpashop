package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member) // 중복 회원 검증 -> unique id로 하는쪽이 낫다
        memberRepository.save(member) // null이 아님이 보장됨

        return member.id!!
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name)

        if (findMembers?.isNotEmpty() == true) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMembers(): MutableList<Member>? {
        return memberRepository.findAll()
    }

    fun findMember(memberId: Long): Member? {
        return memberRepository.findOne(memberId)
    }
}