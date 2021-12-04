package voroby.petstore.dto

import voroby.petstore.model.Order
import javax.validation.constraints.NotBlank

data class OrderDto(
    @NotBlank
    var name: String,

    @NotBlank
    var address: String,

    @NotBlank
    var city: String,

    @NotBlank
    var state: String,

    @NotBlank
    var country: String,

    @NotBlank
    var zip: String,

    var shipped: Boolean,

    var cart: CartDto?
): BaseVersionDto() {
    companion object {
        fun of(order: Order): OrderDto {
            val dto = OrderDto(order.name ?: "", order.address ?: "", order.city ?: "",
                order.state ?: "", order.country ?: "", order.zip ?: "",
                order.shipped, order.cart?.let { CartDto.of(it) }
            )
            dto.id = order.id ?: 0
            dto.version = order.version ?: 0
            return dto
        }
    }
}

fun OrderDto.toOrder(): Order {
    val order = Order(name, address, city, state, country, zip, shipped, cart?.toCart())
    order.id = id
    order.version = version
    return order
}
