package voroby.petstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import voroby.petstore.model.Order
import java.util.stream.Stream

interface OrderRepository: JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.cart where o.name = :name")
    fun findAllByName(name: String): Stream<Order>

}
