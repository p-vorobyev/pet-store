package voroby.petstore.dto

import voroby.petstore.model.Cart
import voroby.petstore.model.CartLine

data class CartDto(
    var id: Long,

    var version: Long,

    var lines: Set<CartLineDto>,

    var itemCount: Int,

    var cartPrice: Double
) {
    companion object {
        fun of(cart: Cart): CartDto {
            val setCartLinesDto: Set<CartLineDto> = cart.cartLines?.map { CartLineDto.of(it) }?.toHashSet() ?: setOf()
            return CartDto(
                cart.id ?: 0,
                cart.version ?: 0,
                setCartLinesDto,
                cart.itemCount,
                cart.cartPrice
            )
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
