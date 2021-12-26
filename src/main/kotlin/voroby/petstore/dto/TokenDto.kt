package voroby.petstore.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TokenDto(
    var success: Boolean,

    var token: String = "",

    var expires: LocalDateTime? = null
)
