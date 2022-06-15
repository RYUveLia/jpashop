package jpabook.jpashop.repository

import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class OrderRepository {
    @PersistenceContext lateinit var entityManager: EntityManager

    fun save(order: Order) {
        entityManager.persist(order)
    }

    fun findOne(id: Long): Order? {
        return entityManager.find(Order::class.java, id)
    }

    // TODO: findall
}