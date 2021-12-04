package voroby.petstore.dto

import voroby.petstore.model.Cart
import voroby.petstore.model.CartLine

data class CartDto(
    var lines: Set<CartLineDto>,

    var itemCount: Int,

    var cartPrice: Double
): BaseVersionDto() {
    companion object {
        fun of(cart: Cart): CartDto {
            val setCartLinesDto: Set<CartLineDto> = cart.cartLines?.map { CartLineDto.of(it) }?.toHashSet() ?: setOf()
            val dto = CartDto(setCartLinesDto, cart.itemCount, cart.cartPrice)
            dto.id = cart.id ?: 0
            dto.version = cart.version ?: 0
            return dto
        }
    }
}

fun CartDto.toCart(): Cart {
    val cartLinesSet: MutableSet<CartLine> = lines.map {
        it.toCartLine()
    }.toMutableSet()
    val cart = Cart(itemCount, cartPrice, cartLinesSet)
    cart.id = id
    cart.version = version
    return cart
}
