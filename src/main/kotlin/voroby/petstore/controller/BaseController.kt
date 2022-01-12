package voroby.petstore.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import reactor.core.publisher.Mono
import voroby.petstore.config.StoreExecutor
import voroby.petstore.service.StoreService
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

abstract class BaseController {

    @Autowired
    lateinit var storeService: StoreService

    @Autowired
    lateinit var storeExecutor: StoreExecutor

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: MethodArgumentNotValidException): Mono<Map<String, String>> {
        val map = hashMapOf<String, String>()
        ex.bindingResult.allErrors.forEach {
            val fieldName = (it as FieldError).field
            val message: String = it.defaultMessage ?: "Undefined error"
            map[fieldName] = message
        }
        return Mono.just(map)
    }

    protected fun <T> futureFromSupplier(supplier: Supplier<T>): CompletableFuture<T> =
        CompletableFuture.supplyAsync({supplier.get()}, storeExecutor.executor::execute)

    protected fun <T> monoResponse(future: CompletableFuture<T>): Mono<ResponseEntity<T>> {
        return Mono
            .fromFuture(future)
            .map { dto -> ResponseEntity.ok().body(dto) }
            .doOnError { ex ->
                Mono.just(ResponseEntity.internalServerError().body(ex.message))
            }
    }

}
