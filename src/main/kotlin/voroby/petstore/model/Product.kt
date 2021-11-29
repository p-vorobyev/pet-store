package voroby.petstore.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "products")
class Product(
    var name: String? = null,

    var category: String? = null,

    var description: String? = null,

    @Column(nullable = false)
    var price: Double = 0.0

) : BaseEntity()
