package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order (
    @Id @GeneratedValue
    @Column(name = "order_id")
    var id: Long?=null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member?=null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = arrayListOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery?=null,

    var orderDate: LocalDateTime?=null,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus?=null
) {
    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun createOrder(member: Member?, delivery: Delivery?, vararg orderItems: OrderItem): Order {
        return Order(member = member, delivery = delivery,
            orderItems = orderItems.toMutableList(), status = OrderStatus.ORDER, orderDate = LocalDateTime.now())
    }

    //==비즈니스 로직==//
    fun cancel() {
        if (delivery!!.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소 불가능")
        }

        this.status = OrderStatus.CANCEL
        orderItems.forEach {
            it.cancel()
        }
    }

    //==조회==//
    fun getTotalPrice(): Int {
        var totalPrice: Int = 0

        orderItems.forEach { totalPrice += it.getTotalPrice() }
        return totalPrice
    }
}