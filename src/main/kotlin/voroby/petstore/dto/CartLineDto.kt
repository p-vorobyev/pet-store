package voroby.petstore.dto

import voroby.petstore.model.CartLine
import voroby.petstore.model.Product

data class CartLineDto(
    var id: Long,

    var version: Long,

    var quantity: Int,

    var product: ProductDto
) {
    companion object {
        fun of(cartLineDto: CartLineDto): CartLine {
            val product: Product = cartLineDto.product.toProduct()
            val cartLine = CartLine(cartLineDto.quantity, product)
            cartLine.id = cartLineDto.id
            cartLine.version = cartLineDto.version
            return  cartLine
        }
    }
}

fun CartLineDto.toCartLine(): CartLine {
    val cartLine = CartLine(quantity, product.toProduct())
    cartLine.id = id
    cartLine.version = version
    return cartLine
}
