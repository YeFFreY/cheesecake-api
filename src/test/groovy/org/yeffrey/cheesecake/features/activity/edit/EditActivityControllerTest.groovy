package org.yeffrey.cheesecake.features.activity.edit

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.create.CreateActivityCommand
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class EditActivityControllerTest extends CheesecakeSpecification implements ActivitiesFixtures {

    void "user can retrieve the details of an activity so he can edit it"() {
        given: "an activity created by bob"
        def activityId = activityClient.create(new CreateActivityCommand(activityName, activityDescription)).body().data()

        when: "bob request activity to edit it (command query mode ;))"
        def response = activityClient.edit(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        def activityCommand = response.body().data()
        activityCommand.name() == activityName
        activityCommand.description() == activityDescription


        where:
        activityName = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()
    }

    void "user receives a 404 when trying to retrieve an activity to edit which does not exists"() {
        given: "an id which does not exists"
        def activityId = UUID.randomUUID()

        when: "bob request activity details"
        def response = activityClient.edit(activityId)

        then:
        response.status() == HttpStatus.NOT_FOUND

        then:
        response.body() == null

    }

    void "user can update an activity"() {
        given: "an activity created by bob"
        def activityId = activityClient.create(new CreateActivityCommand(activityName, activityDescription)).body().data()

        and: "bob request activity to edit it (command query mode ;))"
        activityClient.edit(activityId)

        when: "bob submits some modification"
        def response = activityClient.update(activityId, new EditActivityCommand(newActivityName, newActivityDescription))

        then:
        response.status() == HttpStatus.OK

        then:
        def activityUpdated = activityDetails(response.body().data())
        activityUpdated.name() == newActivityName
        activityUpdated.description() == newActivityDescription

        where:
        activityName = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()
        newActivityName = activityName + "updated"
        newActivityDescription = activityDescription + "updated"
    }

    void "user cannot update an activity with empty name/description"() {
        given: "an activity created by bob"
        def activityId = activityClient.create(new CreateActivityCommand(activityName, activityDescription)).body().data()

        and: "bob request activity to edit it (command query mode ;))"
        activityClient.edit(activityId)

        when: "bob submits some invalid modification"
        activityClient.update(activityId, new EditActivityCommand(invalidActivityName, invalidActivityDescription))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        then: "activity was not updated with wrong values"
        def activityUpdated = activityDetails(activityId)
        activityUpdated.name() == activityName
        activityUpdated.description() == activityDescription

        where:
        activityName = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()
        invalidActivityName = ""
        invalidActivityDescription = ""
    }

    void "user receives a not found error when trying to update an activity that doesn't exist"() {
        expect:
        activityClient.update(activityId, new EditActivityCommand(faker.lorem().sentence(), faker.lorem().sentence()))
                .status() == HttpStatus.NOT_FOUND

        where:
        activityId = UUID.randomUUID()
    }
}
