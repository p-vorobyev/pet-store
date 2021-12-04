package voroby.petstore.dto

import voroby.petstore.model.CartLine
import voroby.petstore.model.Product

data class CartLineDto(
    var quantity: Int,

    var product: ProductDto? = null
): BaseVersionDto() {
    companion object {
        fun of(cartLine: CartLine): CartLineDto {
            val product: Product? = cartLine.product
            val dto = CartLineDto(cartLine.quantity, product?.let { ProductDto.of(it) })
            dto.id = cartLine.id ?: 0
            dto.version = cartLine.version ?: 0
            return dto
        }
    }
}

fun CartLineDto.toCartLine(): CartLine {
    val cartLine = CartLine(quantity, product?.toProduct())
    cartLine.id = id
    cartLine.version = version
    return cartLine
}
