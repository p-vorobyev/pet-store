package voroby.petstore.model

import javax.persistence.*

@Entity
@Table(name = "cart")
class Cart(
    @Column(nullable = false)
    var itemCount: Int = 0,

    @Column(nullable = false)
    var cartPrice: Double = 0.0,

    @OneToMany(
        mappedBy = "cart",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true
    )
    var cartLines: MutableSet<CartLine>? = mutableSetOf()
): BaseEntity()
