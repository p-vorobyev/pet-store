package voroby.petstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(
    transactionManagerRef = "jpaTransaction",
    basePackages = ["voroby.petstore.repository"]
)
class PetStoreApplication

fun main(args: Array<String>) {
    runApplication<PetStoreApplication>(*args)
}
