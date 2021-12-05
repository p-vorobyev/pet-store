package voroby.petstore.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import reactor.core.publisher.Mono

open class BaseController {

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
