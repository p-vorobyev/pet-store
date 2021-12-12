package voroby.petstore.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import voroby.petstore.dto.ProductDto
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

@RestController
@RequestMapping(
    value = ["/products"],
    produces = [APPLICATION_JSON_VALUE]
)
class ProductController: BaseController() {

    @GetMapping
    fun allProducts(): Mono<ResponseEntity<List<ProductDto>>> =
        monoResponse(futureFromSupplier(storeService::getAllProducts))

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun save(@RequestBody dto: ProductDto): Mono<ResponseEntity<ProductDto>> =
        monoResponse(futureFromSupplier { storeService.saveProduct(dto) })

    private fun <T> futureFromSupplier(supplier: Supplier<T>): CompletableFuture<T> =
        CompletableFuture.supplyAsync({supplier.get()}, executor::execute)

    private fun <T> monoResponse(future: CompletableFuture<T>): Mono<ResponseEntity<T>> {
        return Mono
            .fromFuture(future)
            .map { dto -> ResponseEntity.ok().body(dto) }
            .doOnError { ex -> Mono.just(ResponseEntity.internalServerError().body(ex.message)) }
    }

}
