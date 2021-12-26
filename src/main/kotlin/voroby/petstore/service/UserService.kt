package voroby.petstore.service

import org.springframework.stereotype.Service

@Service
class UserService(private val userSet: Set<String> = setOf("anonymous")) {

    fun exist(user: String): Boolean =
        userSet.contains(user)

}
