package voroby.petstore.config

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import reactor.core.publisher.Mono
import voroby.petstore.service.JwtService

class AuthenticationManager(
    private val jwtService: JwtService
    ): ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as String
        return jwtService.validate(token).handle<Authentication> { pair, mon ->
            if (pair.first) {
                UsernamePasswordAuthenticationToken(pair.second, null, mutableListOf(SimpleGrantedAuthority("ROLE_USER")))
            } else {
                mon.error(BadCredentialsException("Token validation failed."))
            }
        }.onErrorResume { Mono.empty() }
    }

}
