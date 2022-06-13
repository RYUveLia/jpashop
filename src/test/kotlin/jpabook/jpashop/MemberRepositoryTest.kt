package jpabook.jpashop

import jpabook.jpashop.ktorm.Model
import jpabook.jpashop.ktorm.ModelRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
class MemberRepositoryTest {
    @Autowired lateinit var modelRepository: ModelRepository

    @Test
    @Transactional
    fun `잘드가니`() {
        // when

        val member = Model {
            username = "alice"
        }

        val savedId = modelRepository.save(member)
        val memberId = modelRepository.find(savedId)!!.id

        // then
        Assertions.assertEquals(memberId, savedId)
    }
}