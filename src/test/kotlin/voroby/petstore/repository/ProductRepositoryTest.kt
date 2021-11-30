package voroby.petstore.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import voroby.petstore.model.Product

class ProductRepositoryTest: DataJpaTest() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun findByNameAndCategory() {
        val product: Product? = productRepository.findByNameAndCategory("Soccer Ball", "Soccer")

        assertNotNull(product)

        product?.let {
            assertEquals("FIFA-approved size and weight", product.description)
            product.description = "new description"
        }

        productRepository.save(product!!)

        val product1 = productRepository.findByNameAndCategory("Soccer Ball", "Soccer")

        product1?.let {
            assertEquals("new description", product1.description)
        }
    }

}
