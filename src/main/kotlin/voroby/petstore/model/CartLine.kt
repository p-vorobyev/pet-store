package voroby.petstore.model

import javax.persistence.*

@Entity
@Table(name = "cart_line")
class CartLine(
    @Column(nullable = false)
    var quantity: Int = 0,

    @OneToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null
): BaseEntity()
