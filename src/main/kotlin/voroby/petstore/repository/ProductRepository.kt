package voroby.petstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints
import voroby.petstore.model.Product
import javax.persistence.QueryHint

interface ProductRepository: JpaRepository<Product, Long> {

    @QueryHints(
        value = [
            QueryHint(name = "org.hibernate.cacheable", value = "true"),
            QueryHint(name = "org.hibernate.cacheRegion", value = "ProductRepository#findByNameAndCategory")
        ]
    )
    fun findByNameAndCategory(name: String, category: String): Product?

}
