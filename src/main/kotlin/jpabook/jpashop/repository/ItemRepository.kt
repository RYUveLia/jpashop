package jpabook.jpashop.repository

import jpabook.jpashop.domain.item.Item
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ItemRepository(){
    @PersistenceContext private lateinit var em: EntityManager

    fun save(item: Item) {
        item.id?.let {
            em.merge(item)
        }?:run {
            em.persist(item)
        }
    }

    fun findOne(id: Long?): Item? {
        return em.find(Item::class.java, id)
    }

    fun findAll(): MutableList<Item>? {
        return em.createQuery("select i from Item i", Item::class.java).resultList
    }
}
//interface ItemRepository: JpaRepository<Item, Long?>