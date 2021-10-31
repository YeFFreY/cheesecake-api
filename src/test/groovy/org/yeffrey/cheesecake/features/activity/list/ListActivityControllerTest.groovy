package org.yeffrey.cheesecake.features.activity.list


import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListActivityControllerTest extends CheesecakeSpecification implements ActivitiesFixtures {

    void "when user creates an activity he can retrieve it"() {
        given: "bob has recorded an activity"
        def activityId = createActivity(activityName, activityDescription)

        when: "bob request the list of his activities"
        var response = activityClient.list()

        then:
        response.status == HttpStatus.OK

        then:
        response.body().data().stream().anyMatch {
            it.id() == activityId
                    && it.name() == activityName
                    && it.description() == activityDescription
        }

        where:
        activityName = faker.lorem().sentence()
        activityDescription = faker.lorem().sentence()

    }
}
