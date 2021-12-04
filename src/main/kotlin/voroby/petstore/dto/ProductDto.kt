package voroby.petstore.dto

import voroby.petstore.model.Product

data class ProductDto(
    var name: String,

    var category: String,

    var description: String,

    var price: Double = 0.0
): BaseVersionDto() {
    companion object {
        fun of(product: Product): ProductDto {
         val dto = ProductDto(product.name ?: "", product.category ?: "",
             product.description ?: "", product.price)
         dto.id = product.id ?: 0
         dto.version = product.version ?: 0
         return dto
        }
    }
}

fun ProductDto.toProduct(): Product {
    val product = Product(name, category, description, price)
    product.id = id
    product.version = version
    return product
}
