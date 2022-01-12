package voroby.petstore.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import voroby.petstore.dto.OrderDto
import voroby.petstore.dto.ProductDto
import voroby.petstore.dto.toOrder
import voroby.petstore.dto.toProduct
import voroby.petstore.model.CartLine
import voroby.petstore.model.Order
import voroby.petstore.model.Product
import voroby.petstore.repository.OrderRepository
import voroby.petstore.repository.ProductRepository
import java.time.Duration
import java.util.*

@Transactional(readOnly = true)
@Service
class StoreService(
    private val orderRepo: OrderRepository,
    private val productRepo: ProductRepository
) {

    fun getAllProducts(): List<ProductDto> =
        productRepo.findAll().map { ProductDto.of(it) }

    fun streamAllProducts(): Flux<ProductDto> =
        Flux
            .fromStream(productRepo.findAll().map { ProductDto.of(it) }.stream())
            .delayElements(Duration.ofSeconds(1))

    @Transactional
    fun saveProduct(dto: ProductDto): ProductDto =
        ProductDto.of(productRepo.save(dto.toProduct()))

    @Transactional
    fun deleteProduct(id: Long): Boolean {
        val product: Optional<Product> = productRepo.findById(id)
        return product.map {
            productRepo.deleteById(id)
            true
        }.orElseGet{ false }
    }

    fun getAllOrders(): List<OrderDto> =
        orderRepo.findAll().map { OrderDto.of(it) }

    @Transactional
    fun saveOrder(dto: OrderDto): OrderDto {
        val order: Order = orderRepo.save(orderDtoToJpa(dto))
        return OrderDto.of(order)
    }

    private fun orderDtoToJpa(dto: OrderDto): Order {
        val order = dto.toOrder()
        val cart = order.cart
        if (cart != null) {
            val cartLines: MutableSet<CartLine> = cart.cartLines ?: mutableSetOf()
            val ids: List<Long?> = cartLines.map(CartLine::product).map { it?.id }.toList()
            val products: MutableList<Product> = productRepo.findAllById(ids)
            if (ids.size == products.size) {
                for((index, cartLine) in cartLines.withIndex()) {
                    cartLine.product = products[index]
                    cartLine.cart = cart
                }
            } else throw IllegalStateException("Products are not found in the database.")
        }

        return order
    }

    @Transactional
    fun deleteOrder(id: Long): Boolean {
        val order: Optional<Order> = orderRepo.findById(id)
        return order.map {
            orderRepo.deleteById(id)
            true
        }.orElse(false)
    }
}
