package org.yeffrey.cheesecake.features.activityoperation.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityoperation.fixtures.ActivityOperationFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListActivityOperationControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityOperationFixtures {

    void "user can retrieve the list of operations associated with a given activty"() {
        given: "2 activity and some operations associated"
        def activityId = createActivity()
        createActivityOperation(activityId, "CORE")
        createActivityOperation(activityId, "SETUP")
        def activityId2 = createActivity()
        createActivityOperation(activityId2, "CORE")
        createActivityOperation(activityId2, "SETUP")
        createActivityOperation(activityId2, "CLOSING")


        when:
        def response = activityOperationClient.list(activityId)

        then:
        response.status == HttpStatus.OK

        then:
        def operations = response.body().data()
        operations*.typeId().sort() == ['CORE', 'SETUP'].sort()

    }

    void "when no operations associated to activity, get an empty list"() {
        given: "an activity without operations"
        def activityId = createActivity()

        when:
        def response = activityOperationClient.list(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }

    void "when retrieving operations from unknown activity, get empty result"() {
        when:
        def response = activityOperationClient.list(UUID.randomUUID())

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }
}
