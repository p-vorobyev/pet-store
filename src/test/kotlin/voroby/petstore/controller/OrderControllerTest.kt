package voroby.petstore.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong
import org.springframework.http.MediaType.APPLICATION_JSON
import reactor.core.publisher.Mono
import voroby.petstore.AbstractMvcTest
import voroby.petstore.dto.CartDto
import voroby.petstore.dto.CartLineDto
import voroby.petstore.dto.OrderDto
import voroby.petstore.dto.ProductDto

internal class OrderControllerTest: AbstractMvcTest() {

    private val URI = "/orders"

    private val dto: OrderDto

    init {
        val cartLine = CartLineDto(2, ProductDto(name = "t-shirt", description = "white t-shirt", category = "Sportswear", price = 22.5))
        val cart = CartDto(itemCount = 1, cartPrice = 45.0, lines = setOf(cartLine))
        dto = OrderDto(
            name = "My Order",
            address = "Lenin avenue",
            city = "Leningrad",
            state = "Unknown",
            country = "USSR",
            zip = "123456",
            shipped = false,
            cart = cart
        )
    }

    @Test
    fun save() {
        val saved = dto.copy()
        saved.id = 1
        saved.version = 1

        `when`(service.saveOrder(dto))
            .thenReturn(saved)

        val result = webClient.post()
            .uri(URI)
            .contentType(APPLICATION_JSON)
            .body(Mono.just(dto), OrderDto::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody(OrderDto::class.java)
            .returnResult()

        val responseBody = result.responseBody

        assertEquals(saved, responseBody)
    }

    @Test
    fun getAll() {
        val list = listOf(dto)

        `when`(service.getAllOrders())
            .thenReturn(list)

        val result = webClient.get()
            .uri(URI)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(OrderDto::class.java)
            .returnResult()

        val responseBody: MutableList<OrderDto>? = result.responseBody

        assertNotNull(responseBody)

        responseBody?.let {
            assertTrue(list.containsAll(it))
        }
    }

    @Test
    fun delete() {
        `when`(service.deleteOrder(anyLong()))
            .thenReturn(true)

        val returnResult = webClient.delete()
            .uri("$URI/1")
            .exchange()
            .expectStatus().isOk
            .expectBody(Boolean::class.java)
            .returnResult()

        val responseBody = returnResult.responseBody

        assertNotNull(responseBody)

        responseBody?.let {
            assertTrue(responseBody)
        }
    }
}
