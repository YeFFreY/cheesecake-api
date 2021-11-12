package org.yeffrey.cheesecake.features.equipment.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.equipment.fixtures.EquipmentsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateEquipmentControllerTest extends CheesecakeSpecification implements EquipmentsFixtures {

    void "user creates a new equipment"() {
        given: "valid details of a new equipment"
        def command = new CreateEquipmentCommand(equipmentName, equipmentDescription)

        when:
        var response = equipmentClient.create(command)

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        equipmentName = faker.lorem().sentence()
        equipmentDescription = faker.lorem().sentence()
    }

    void "new equipment requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateEquipmentCommand(equipmentName, equipmentDescription)

        when:
        equipmentClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        equipmentName = ""
        equipmentDescription = ""
    }

}
