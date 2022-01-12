package voroby.petstore.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import voroby.petstore.dto.TokenDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

private const val EXPIRATION_PERIOD_MILLIS = 1_800_000L

private const val SECRET = "pet-store"

@Service
class JwtService(private val userService: UserService) {

    companion object: Log()

    fun validate(token: String): Mono<Pair<Boolean, String?>> {
        var pair: Pair<Boolean, String?> = Pair(false, null)
        try {
            val subject: String = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).subject
            if (userService.exist(subject))
                pair = Pair(true, subject)
        } catch (e: JWTVerificationException) {
            log.warn("JWTVerificationException: [message: ${e.message}]")
        }

        return Mono.just(pair)
    }

    fun generateToken(subject: String): Mono<TokenDto> {
        val expiresAt = Instant.now().plusMillis(EXPIRATION_PERIOD_MILLIS)
        return Mono.just(expiresAt)
            .map { instant ->
                JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(Date.from(instant))
                    .sign(Algorithm.HMAC256(SECRET))
            }.map { token ->
                TokenDto(true, token, LocalDateTime.ofInstant(expiresAt, ZoneId.systemDefault()))
            }
    }
}
