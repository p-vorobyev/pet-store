package voroby.petstore.dto

import voroby.petstore.model.Product

data class ProductDto(
    var id: Long,

    var version: Long,

    var name: String,

    var category: String,

    var description: String,

    var price: Double = 0.0
) {
    companion object {
        fun of(product: Product): ProductDto =
            ProductDto(
                product.id ?: 0,
                product.version ?: 0,
                product.name ?: "",
                product.category ?: "",
                product.description ?: "",
                product.price
            )
    }
}

fun ProductDto.toProduct(): Product {
    val product = Product(name, category, description, price)
    product.id = id
    product.version = version
    return product
}
