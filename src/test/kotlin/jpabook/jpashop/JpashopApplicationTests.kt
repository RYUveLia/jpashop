package jpabook.jpashop

import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
class JpashopApplicationTests {
	@Autowired lateinit var memberRepository: MemberRepository
	@PersistenceContext lateinit var entityManager: EntityManager

	/*@BeforeEach
	fun setup() {
		memberRepository.save(Member(username = "alice"))
		memberRepository.save(Member(username = "bob"))
	}*/

	@Test
	//@Transactional
	@Rollback(false)
	fun testMember() {
		// given
		var member = Member(username="alice")

		// when
		var saveId = memberRepository.save(member).id
		var findMember = memberRepository.findById(saveId).get()

		// then
		Assertions.assertEquals(findMember.id, member.id)
		Assertions.assertEquals(findMember.username, member.username)
		Assertions.assertEquals(findMember, member)
	}

}
