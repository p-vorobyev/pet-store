package voroby.petstore.dto

import java.time.LocalDateTime

data class TokenDto(
    var success: Boolean,

    var token: String,

    var expires: LocalDateTime
)
