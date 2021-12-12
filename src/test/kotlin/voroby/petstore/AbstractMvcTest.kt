package voroby.petstore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import voroby.petstore.config.NettyConfiguration
import voroby.petstore.controller.ProductController
import voroby.petstore.repository.OrderRepository
import voroby.petstore.repository.ProductRepository
import voroby.petstore.service.StoreService

@WebFluxTest(
    controllers = [
        ProductController::class
    ]
)
@Import(NettyConfiguration::class)
abstract class AbstractMvcTest: AbstractProfileTest() {

    @Autowired
    lateinit var webClient: WebTestClient

    @MockBean
    lateinit var service: StoreService

    @MockBean
    lateinit var productRepository: ProductRepository

    @MockBean
    lateinit var orderRepository: OrderRepository

}
