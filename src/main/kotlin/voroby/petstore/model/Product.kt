package voroby.petstore.model

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Cacheable
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE,
    region = "Product"
)
@Entity
@Table(name = "products")
class Product(
    var name: String? = null,

    var category: String? = null,

    var description: String? = null,

    @Column(nullable = false)
    var price: Double = 0.0

) : BaseEntity()
