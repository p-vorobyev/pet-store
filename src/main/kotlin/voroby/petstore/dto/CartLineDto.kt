package voroby.petstore.dto

import voroby.petstore.model.CartLine
import voroby.petstore.model.Product

data class CartLineDto(
    var id: Long,

    var version: Long,

    var quantity: Int,

    var product: ProductDto? = null
) {
    companion object {
        fun of(cartLine: CartLine): CartLineDto {
            val product: Product? = cartLine.product
            return CartLineDto(
                cartLine.id ?: 0,
                cartLine.version ?: 0,
                cartLine.quantity,
                product?.let { ProductDto.of(it) }
            )
        }
    }
}

fun CartLineDto.toCartLine(): CartLine {
    val cartLine = CartLine(quantity, product?.toProduct())
    cartLine.id = id
    cartLine.version = version
    return cartLine
}
