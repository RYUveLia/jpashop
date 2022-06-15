package jpabook.jpashop

import jpabook.jpashop.domain.item.Album
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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
class itemRepositoryTest {
    @Autowired lateinit var itemRepository: ItemRepository
    @PersistenceContext lateinit var em: EntityManager

    @Test
    fun `아이템 수량 넣기`() {
        var item: Item = Album("이승철", "몰라")

        //item.stockQuantity = 3

        itemRepository.save(item)
        //itemRepository.save(item)

        val getItem = itemRepository.findOne(item.id)

        Assertions.assertEquals(item, getItem)

        println(item.stockQuantity)
    }
}