package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired lateinit var orderService: OrderService
    @Autowired lateinit var orderRepository: OrderRepository
    @PersistenceContext
    lateinit var em: EntityManager

    @Test
    fun `상품 주문`() {
        // given
        var member = createMember()
        var book = createBook()

        val orderCount = 2

        // when
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)!!
        em.flush()

        // then
        val getOrder = orderRepository.findOne(orderId)!!

        println(getOrder.orderItems.size)

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.status)
        Assertions.assertEquals(1, getOrder.orderItems.size)
        Assertions.assertEquals(10000 * orderCount, getOrder.getTotalPrice())
        Assertions.assertEquals(8, book.stockQuantity)
    }

    private fun createBook(): Book {
        var book = Book()
        book.name = "시골jpa"
        book.price = 10000
        book.stockQuantity = 10
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        var member = Member(name = "회원1", address = Address("서울", "강가", "123123"))
        em.persist(member)
        return member
    }

    @Test
    fun `상품 주문 재고 수량 초과`() {
        //given
        var member = createMember()
        var book = createBook()

        val orderCount = 11

        //when
        try {
            orderService.order(member.id!!, book.id!!, orderCount)
        } catch (e: NotEnoughStockException) {
            return
        }

        //thn
        fail("예외 발생 왜안함?")
    }

    @Test
    fun `주문 취소`() {
        //given
        var member = createMember()
        var item = createBook()

        val orderCount = 2

        val orderId = orderService.order(member.id!!, item.id!!, orderCount)

        //when
        orderId?.let { orderService.cancelOrder(it) }

        //then
        var getOrder = orderId?.let { orderRepository.findOne(it) }!!

        Assertions.assertEquals(OrderStatus.CANCEL, getOrder.status)
        Assertions.assertEquals(10, item.stockQuantity)
    }
}