package voroby.petstore.model

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    var name: String? = null,

    var address: String? = null,

    var city: String? = null,

    var state: String? = null,

    var country: String? = null,

    var zip: String? = null,

    @Column(nullable = false)
    var shipped: Boolean = false,

    @OneToOne(
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null
) : BaseEntity()
