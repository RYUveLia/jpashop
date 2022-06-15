package jpabook.jpashop.domain

import javax.persistence.*

@Entity
@Table(name = "delivery")
class Delivery (
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id: Long? = null,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus? = null
)