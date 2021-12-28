package voroby.petstore.service

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono
import voroby.petstore.AbstractMockTest
import voroby.petstore.dto.TokenDto

internal class JwtServiceTest: AbstractMockTest() {

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var jwtService: JwtService

    @Test
    fun validate() {
        var pair: Pair<Boolean, String?>? = null

        `when`(userService.exist(anyString()))
            .thenReturn(true)

        jwtService.generateToken("user")
            .flatMap { jwtService.validate(it.token) }
            .subscribe { pair = it }

        assertNotNull(pair)

        pair?.let {
            assertTrue(it.first)
            assertNotNull(it.second)
        }
    }

    @Test
    fun generateToken() {
        val monoToken: Mono<TokenDto> = jwtService.generateToken("user")

        monoToken.subscribe{ tokenDto ->
            assertNotNull(tokenDto)
            assertTrue(tokenDto.success)
            assertTrue(tokenDto.token.isNotEmpty())
        }
    }

}
