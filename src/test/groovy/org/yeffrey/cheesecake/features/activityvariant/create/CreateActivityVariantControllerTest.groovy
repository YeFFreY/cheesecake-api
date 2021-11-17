package org.yeffrey.cheesecake.features.activityvariant.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityvariant.fixtures.ActivityVariantFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateActivityVariantControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityVariantFixtures {

    void "user add a variant to an activity"() {
        given: "an activity"
        def activityId = createActivity()


        when: "adding a variant to the activity"
        var response = activityVariantClient.create(new CreateActivityVariantCommand(activityId, faker.lorem().sentence(), faker.lorem().sentence()))

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null
    }

    void "variant requires both an activity id and description"() {

        when: "associate a variant to activity"
        activityVariantClient.create(new CreateActivityVariantCommand(activityId, name, description))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | name                     | description
        UUID.randomUUID() | null                     | null
        null              | faker.lorem().sentence() | null
        null              | null                     | faker.lorem().sentence()
        UUID.randomUUID() | faker.lorem().sentence() | null
        UUID.randomUUID() | null                     | faker.lorem().sentence()
        null              | faker.lorem().sentence() | faker.lorem().sentence()
        null              | null                     | null
    }

    void "user cannot associate a variant to an unknown activity"() {
        given: "an unknown activity"
        def activityId = UUID.randomUUID()

        when:
        def response = activityVariantClient.create(new CreateActivityVariantCommand(activityId, faker.lorem().sentence(), faker.lorem().sentence()))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

}