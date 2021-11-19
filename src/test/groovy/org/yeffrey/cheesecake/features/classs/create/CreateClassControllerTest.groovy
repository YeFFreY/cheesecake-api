package org.yeffrey.cheesecake.features.classs.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateClassControllerTest extends CheesecakeSpecification implements ClassFixtures {

    void "user creates a new class"() {
        given: "valid details of a new class"
        def command = new CreateClassCommand(className, classDescription)

        when: "creates class"
        var response = classClient.create(command)

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        className = faker.lorem().sentence()
        classDescription = faker.lorem().sentence()
    }

    void "new class requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateClassCommand(classTitle, classDescription)

        when: "creates class"
        classClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        classTitle = ""
        classDescription = ""
    }

}
