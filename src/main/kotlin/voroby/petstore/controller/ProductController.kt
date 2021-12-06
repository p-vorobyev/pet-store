package voroby.petstore.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import voroby.petstore.dto.ProductDto
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping(
    value = ["/products"],
    produces = [APPLICATION_JSON_VALUE]
)
class ProductController: BaseController() {

    @GetMapping
    fun allProducts(): Mono<ResponseEntity<List<ProductDto>>> {
        val future: CompletableFuture<List<ProductDto>> =
            CompletableFuture.supplyAsync({ storeService.getAllProducts() }, { executor })
        return Mono.fromFuture(future)
            .map { products -> ResponseEntity.ok().body(products) }
    }

}
