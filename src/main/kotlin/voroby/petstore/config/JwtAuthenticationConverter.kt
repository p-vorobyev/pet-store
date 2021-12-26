package voroby.petstore.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtAuthenticationConverter: ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
            .flatMap {
                val header: String? = it.request.headers.getFirst("Authorization")
                header?.let {
                    header.replace("Bearer ", "")
                }
                Mono.justOrEmpty(header)
            }
            .filter { it.isNotBlank() }
            .map { token -> UsernamePasswordAuthenticationToken("", token) }

    }

}
