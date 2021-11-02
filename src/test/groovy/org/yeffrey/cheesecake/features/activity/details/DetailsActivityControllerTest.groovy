package org.yeffrey.cheesecake.features.activity.details

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.create.CreateActivityCommand
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class DetailsActivityControllerTest extends CheesecakeSpecification implements ActivitiesFixtures {

    void "user can retrieve the details of an activity he created"() {
        given: "an activity created by bob"
        def activityId = activityClient.create(new CreateActivityCommand(activityName, activityDescription)).body().data()

        when: "bob request activity details"
        def response = activityClient.details(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        def activityDetails = response.body().data()
        activityDetails.name() == activityName
        activityDetails.description() == activityDescription


        where:
        activityName = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()
    }

    void "user receives a 404 when trying to retrieve an activity which does not exists"() {
        given: "an id which does not exists"
        def activityId = UUID.randomUUID()

        when: "bob request activity details"
        def response = activityClient.details(activityId)

        then:
        response.status() == HttpStatus.NOT_FOUND

        then:
        response.body() == null

    }
}
