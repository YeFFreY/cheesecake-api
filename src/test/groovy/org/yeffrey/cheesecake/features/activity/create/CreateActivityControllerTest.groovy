package org.yeffrey.cheesecake.features.activity.create


import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateActivityControllerTest extends CheesecakeSpecification implements ActivitiesFixtures {

    void "user creates a new activity"() {
        given: "valid details of a new activity"
        def command = new CreateActivityCommand(activityTitle, activityDescription)

        when: "creates activity"
        var response = activityClient.create(command)

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        activityTitle = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()
    }

    void "new activity requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateActivityCommand(activityTitle, activityDescription)

        when: "creates activity"
        activityClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityTitle = ""
        activityDescription = ""
    }

}
