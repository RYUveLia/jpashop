package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
@Table(name = "order_items")
class OrderItem (
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order?=null,

    var orderPrice: Int=0,
    var count: Int=0
) {
    fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
        this.item = item
        this.orderPrice = orderPrice
        this.count = count

        item.removeStock(count)
        return this
    }

    //==비즈니스 로직==//
    fun cancel() {
        this.item!!.addStock(count)
    }

    //==조회==//
    fun getTotalPrice(): Int {
        return count * orderPrice
    }
}