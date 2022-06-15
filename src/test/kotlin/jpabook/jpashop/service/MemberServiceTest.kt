package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.service.MemberService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired lateinit var memberService: MemberService
    lateinit var memberRepository: MemberRepository
    @PersistenceContext lateinit var em: EntityManager

    @Test
    //@Rollback(false)
    fun `회원가입 테스트`() {
        // given
        val address = Address("서울", "가로수길", "0000")
        var member = Member(name = "kim", address = address)

        // when
        val savedId = memberService.join(member)

        // then
        em.flush()
        Assertions.assertEquals(member, memberService.findMember(savedId))
    }

    @Test
    fun `회원가입 중복 테스트`() {
        // given
        val address = Address("서울", "가로수길", "0000")
        var member1 = Member(name = "kim", address = address)
        var member2 = Member(name = "kim", address = address)

        // when
        memberService.join(member1)

        try {
            memberService.join(member2)
        } catch (e: IllegalStateException) {
            return
        }

        //then
        fail("예외 발생해야함")
    }
}