package org.yeffrey.cheesecake.features.activityoperation.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityoperation.fixtures.ActivityOperationFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateActivityOperationControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityOperationFixtures {

    void "user add an operation to an activity"() {
        given: "an activity"
        def activityId = createActivity()


        when: "adding an operation to the activity"
        var response = activityOperationClient.create(new CreateActivityOperationCommand(activityId, "CORE", faker.lorem().sentence()))

        then:
        response.status == HttpStatus.CREATED
    }

    void "user cannot associate the same operation type multiple times to the same activity"() {
        given: "an activity and an operation CORE already associated"
        def activityId = createActivity()
        activityOperationClient.create(new CreateActivityOperationCommand(activityId, "CORE", faker.lorem().sentence()))

        when: "associate again the same operation type to the same activity"
        activityOperationClient.create(new CreateActivityOperationCommand(activityId, "CORE", faker.lorem().sentence()))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.UNPROCESSABLE_ENTITY
    }


    void "association requires both an activity id, type and description"() {

        when: "associate an operation to activity"
        activityOperationClient.create(new CreateActivityOperationCommand(activityId, type, description))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | type   | description
        null              | null   | null
        UUID.randomUUID() | null   | null
        null              | "CORE" | null
        null              | null   | faker.lorem().sentence()
        null              | "CORE" | faker.lorem().sentence()
        UUID.randomUUID() | null   | faker.lorem().sentence()
        UUID.randomUUID() | "CORE" | null
        UUID.randomUUID() | "CORE" | " "
        UUID.randomUUID() | " "    | faker.lorem().sentence()
    }

    void "user cannot associate an operation to an unknown activity"() {
        given: "an unknown activity and an equipment"
        def activityId = UUID.randomUUID()

        when:
        def response = activityOperationClient.create(new CreateActivityOperationCommand(activityId, "CORE", faker.lorem().sentence()))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user cannot associate an invalid equipment to an activity"() {
        given: "an activity and an unknown equipment"
        def activityId = createActivity()

        when:
        def response = activityOperationClient.create(new CreateActivityOperationCommand(activityId, "INVALID", faker.lorem().sentence()))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user can retrieve the list of operation types not yet created for the given activity"() {
        given: "2 activities"
        def activityId = createActivity()
        def activityId2 = createActivity()

        and: "an operation of type CORE added to activity 1 and operation type CLOSING added to activity 2"
        activityOperationClient.create(new CreateActivityOperationCommand(activityId, "CORE", faker.lorem().sentence()))
        activityOperationClient.create(new CreateActivityOperationCommand(activityId2, "CLOSING", faker.lorem().sentence()))

        when:
        def response = activityOperationClient.listAvailableOperationTypes(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data()*.id() == ["SETUP", "CLOSING"]
    }
}