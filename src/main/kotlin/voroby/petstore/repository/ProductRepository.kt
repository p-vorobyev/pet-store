package voroby.petstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import voroby.petstore.model.Product
import javax.persistence.QueryHint

const val allProducts = "select p from Product p"

interface ProductRepository: JpaRepository<Product, Long> {

    @QueryHints(
        value = [
            QueryHint(name = "org.hibernate.cacheable", value = "true"),
            QueryHint(name = "org.hibernate.cacheRegion", value = "ProductRepository#findByNameAndCategory")
        ]
    )
    fun findByNameAndCategory(name: String, category: String): Product?

    @QueryHints(
        value = [
            QueryHint(name = "org.hibernate.cacheable", value = "true"),
            QueryHint(name = "org.hibernate.cacheRegion", value = "ProductRepository#findAll")
        ]
    )
    @Query(allProducts)
    override fun findAll(): MutableList<Product>

}
