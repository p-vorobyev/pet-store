package voroby.petstore.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import voroby.petstore.dto.TokenDto
import voroby.petstore.dto.UserDto
import voroby.petstore.service.JwtService
import voroby.petstore.service.UserService

@RestController
@RequestMapping(value = ["/api/login"], produces = [APPLICATION_JSON_VALUE])
class LoginController(
    private val userService: UserService,
    private val jwtService: JwtService
    ): BaseController() {

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun login(@RequestBody userDto: UserDto): Mono<ResponseEntity<TokenDto>> {
        val user = userDto.username.lowercase()
        if (userService.exist(user)) {
            return jwtService.generateToken(user)
                .map { tokenDto ->
                    ResponseEntity.ok().body(tokenDto)
                }
        }

        return Mono.just(ResponseEntity.ok().body(TokenDto(false)))
    }

}
