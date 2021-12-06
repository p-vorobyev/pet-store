package voroby.petstore.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import reactor.core.publisher.Mono
import voroby.petstore.service.StoreService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BaseController {

    @Autowired
    lateinit var storeService: StoreService

    val executor: ExecutorService = Executors.newCachedThreadPool()

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

}
