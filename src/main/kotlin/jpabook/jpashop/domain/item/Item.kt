package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item{
    @Id @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    var name: String? = null
    var price: Int = 0
    var stockQuantity: Int = 0

    @ManyToMany(mappedBy = "items")
    var categories: MutableList<Category> = arrayListOf()

    //==비즈니스 로직==//
    fun addStock(quantity: Int) {
        this.stockQuantity = this.stockQuantity.plus(quantity)
    }

    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity.minus(quantity)
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        } else {
            this.stockQuantity = restStock
        }
    }
}