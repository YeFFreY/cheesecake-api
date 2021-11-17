package org.yeffrey.cheesecake.features.activityvariant.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityvariant.fixtures.ActivityVariantFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListActivityVariantControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityVariantFixtures {

    void "user can retrieve the list of variants associated with a given activty"() {
        given: "2 activity and some operations associated"
        def activityId = createActivity()
        def variantId = createActivityVariant(activityId)
        def variantId2 = createActivityVariant(activityId)
        def activityId2 = createActivity()
        createActivityVariant(activityId2)
        createActivityVariant(activityId2)
        createActivityVariant(activityId2)


        when:
        def response = activityVariantClient.list(activityId)

        then:
        response.status == HttpStatus.OK

        then:
        def variants = response.body().data()
        variants*.id() == [variantId, variantId2]


    }

    void "when no variants associated to activity, get an empty list"() {
        given: "an activity without operations"
        def activityId = createActivity()

        when:
        def response = activityVariantClient.list(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }

    void "when retrieving variants from unknown activity, get empty result"() {
        when:
        def response = activityVariantClient.list(UUID.randomUUID())

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }
}
