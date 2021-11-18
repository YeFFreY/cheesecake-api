package org.yeffrey.cheesecake.features.activityoperation.delete

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityoperation.fixtures.ActivityOperationFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class DeleteActivityOperationControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityOperationFixtures {
    void "user is able to remove an associated operation from the list of operations of an activity"() {
        given: "an activity and some operations"
        def activityId = createActivity()
        createActivityOperation(activityId, "CORE")
        createActivityOperation(activityId, "SETUP")

        when:
        def response = activityOperationClient.delete(new DeleteActivityOperationCommand(activityId, "SETUP"))

        then:
        response.status == HttpStatus.OK

        then:
        def equipments = listActivityOperations(activityId)
        equipments.size() == 1
        equipments*.typeId() == ["CORE"]

    }

    void "when user try to remove an operation not associated with an activity it returns not found"() {
        given: "2 activities and some operations"
        def activityId = createActivity()
        createActivityOperation(activityId, "CORE")
        def activityId2 = createActivity()
        createActivityOperation(activityId2, "SETUP")

        when: "user remove operation SETUP which is not associated to activity 1"
        def response = activityOperationClient.delete(new DeleteActivityOperationCommand(activityId, "SETUP"))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "when user try to remove an operation not associated from an activity which does not exist it returns not found"() {
        given: "an unknown activity id"
        def activityId = UUID.randomUUID()

        when: "user remove operation which is not associated, and from an activity which doesn't exist"
        def response = activityOperationClient.delete(new DeleteActivityOperationCommand(activityId, "CORE"))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "delete requires both an activity id and operation type"() {
        when: "delete operation associated and activity with invalid input"
        activityOperationClient.delete(new DeleteActivityOperationCommand(activityId, typeId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | typeId
        null              | null
        UUID.randomUUID() | null
        null              | "CORE"
    }

}
