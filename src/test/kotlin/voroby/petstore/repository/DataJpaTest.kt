package voroby.petstore.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import voroby.petstore.AbstractProfileTest

@DataJpaTest
abstract class DataJpaTest: AbstractProfileTest() {
}
