package voroby.petstore.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong
import org.springframework.http.MediaType
import reactor.core.publisher.Mono
import voroby.petstore.AbstractMvcTest
import voroby.petstore.dto.ProductDto

internal class ProductControllerTest: AbstractMvcTest() {

    private val URI = "/products"

    @Test
    fun allProducts() {
        mockSecurity()

        val dto = ProductDto("Ball", "Sport", "For soccer", 45.0)
        dto.id = 1
        dto.version = 1
        `when`(service.getAllProducts())
            .thenReturn(listOf(dto))

        val returnResult = webClient.get()
            .uri(URI)
            .header("Authorization", bearer)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(ProductDto::class.java)
            .contains(dto)
            .returnResult()

        val responseBody: MutableList<ProductDto>? = returnResult.responseBody

        assertTrue(responseBody?.size == 1)
    }

    @Test
    fun save() {
        mockSecurity()

        val dto = ProductDto("Shoes", "Travel", "Good shoes for adventure", 150.0)

        val saved = dto.copy()
        saved.id = 1
        saved.version = 1
        `when`(service.saveProduct(dto))
            .thenReturn(saved)

        val result = webClient.post()
            .uri(URI)
            .header("Authorization", bearer)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(dto), ProductDto::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody(ProductDto::class.java).returnResult()

        val responseDto = result.responseBody

        assertNotNull(responseDto)

        responseDto?.let {
            assertEquals(1, it.id)
            assertEquals(1, it.version)
        }
    }

    @Test
    fun delete() {
        mockSecurity()

        `when`(service.deleteProduct(anyLong()))
            .thenReturn(true)

        val result = webClient.delete()
            .uri("$URI/1")
            .header("Authorization", bearer)
            .exchange()
            .expectStatus().isOk
            .expectBody(Boolean::class.java).returnResult()

        val body = result.responseBody

        assertNotNull(body)

        body?.let {
            assertTrue(body)
        }
    }
}
