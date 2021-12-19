package voroby.petstore.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import voroby.petstore.dto.OrderDto

@RestController
@RequestMapping(
    value = ["/orders"],
    produces = [APPLICATION_JSON_VALUE])
class OrderController: BaseController() {

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun save(@RequestBody dto: OrderDto): Mono<ResponseEntity<OrderDto>> =
        monoResponse(futureFromSupplier { storeService.saveOrder(dto) })

    @GetMapping
    fun getAll(): Mono<ResponseEntity<List<OrderDto>>> =
        monoResponse(futureFromSupplier { storeService.getAllOrders() })

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<ResponseEntity<Boolean>> =
        monoResponse(futureFromSupplier { storeService.deleteOrder(id) })

}
