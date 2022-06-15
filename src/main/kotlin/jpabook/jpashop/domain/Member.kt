package jpabook.jpashop.domain

import au.com.console.kassava.*
import javax.persistence.*

@Entity
class Member (
    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,
    var name: String,

    @Embedded
    var address: Address,

    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = arrayListOf()
) {
    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Member::id)
        private val toStringProperties = arrayOf(
            Member::id,
            Member::name
        )
    }
}