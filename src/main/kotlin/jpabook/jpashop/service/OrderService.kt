package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService {
    @Autowired lateinit var orderRepository: OrderRepository
    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var itemRepository: ItemRepository

    // 주문
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long? {

        //엔티티 조회
        val member = memberRepository.findOne(memberId)
        val item = itemRepository.findOne(itemId)

        //배송정보 생성
        val delivery = Delivery(address = member?.address)

        //상품 생성
        val orderItem = OrderItem()
        item?.let { orderItem.createOrderItem(it, it.price, count) }

        //주문 생성
        val order = Order().createOrder(member, delivery, orderItem)

        // cascade.all 옵션을 통해 order를 persist 하면 나머지도 알아서 persist됨
        // a->b 일때 b를 참조하는 곳이 a가 유일하면 cascade all
        orderRepository.save(order)

        return order.id
    }

    // 취소
    @Transactional
    fun cancelOrder(orderId: Long) {
        // fineOne밖에 안했는데 update쿼리 안날림? -> jpa가 변경 감지하고 알아서 업데이트 해줌(dirty-checking)
        orderRepository.findOne(orderId)?.cancel()
    }

    // 검색
/*
    fun findOrders(orderSearch: OrderSearch) {
        return orderRepository.findAll(orderSearch)
    }
    */
}