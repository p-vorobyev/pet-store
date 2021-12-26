package voroby.petstore

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import voroby.petstore.config.NettyConfiguration
import voroby.petstore.controller.LoginController
import voroby.petstore.controller.OrderController
import voroby.petstore.controller.ProductController
import voroby.petstore.dto.TokenDto
import voroby.petstore.dto.UserDto
import voroby.petstore.repository.OrderRepository
import voroby.petstore.repository.ProductRepository
import voroby.petstore.service.JwtService
import voroby.petstore.service.StoreService
import voroby.petstore.service.UserService

@WebFluxTest(
    controllers = [
        ProductController::class,
        OrderController::class,
        LoginController::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(value = [NettyConfiguration::class, PetStoreApplication::class])
abstract class AbstractMvcTest: AbstractProfileTest() {

    @Autowired
    lateinit var webClient: WebTestClient

    @MockBean
    lateinit var service: StoreService

    @MockBean
    lateinit var jwtService: JwtService

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var productRepository: ProductRepository

    @MockBean
    lateinit var orderRepository: OrderRepository

    var bearer = "Bearer "

    @BeforeAll
    fun login() {
        val userDto = UserDto("anonymous")
        val returnResult: EntityExchangeResult<TokenDto> = webClient.post()
            .uri("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(userDto), UserDto::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody(TokenDto::class.java).returnResult()

        val tokenDto: TokenDto? = returnResult.responseBody

        assertNotNull(tokenDto)

        assertEquals(true, tokenDto?.success)

        bearer.plus(tokenDto?.token)
    }

}
