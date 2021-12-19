package voroby.petstore.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import voroby.petstore.dto.ProductDto

@RestController
@RequestMapping(
    value = ["/products"],
    produces = [APPLICATION_JSON_VALUE]
)
class ProductController: BaseController() {

    @GetMapping
    fun getAll(): Mono<ResponseEntity<List<ProductDto>>> =
        monoResponse(futureFromSupplier(storeService::getAllProducts))

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun save(@RequestBody dto: ProductDto): Mono<ResponseEntity<ProductDto>> =
        monoResponse(futureFromSupplier { storeService.saveProduct(dto) })

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<ResponseEntity<Boolean>> =
        monoResponse(futureFromSupplier { storeService.deleteProduct(id) })

}
