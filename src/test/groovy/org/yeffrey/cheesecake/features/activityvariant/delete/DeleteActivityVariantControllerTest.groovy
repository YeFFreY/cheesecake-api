package org.yeffrey.cheesecake.features.activityvariant.delete

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityvariant.fixtures.ActivityVariantFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class DeleteActivityVariantControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, ActivityVariantFixtures {
    void "user is able to remove a variant from the list of variants of an activity"() {
        given: "an activity and some variants"
        def activityId = createActivity()
        def variantId = createActivityVariant(activityId)
        def variantId2 = createActivityVariant(activityId)

        when:
        def response = activityVariantClient.delete(new DeleteActivityVariantCommand(activityId, variantId))

        then:
        response.status == HttpStatus.OK

        then:
        def variants = listActivityVariants(activityId)
        variants.size() == 1
        variants*.id() == [variantId2]

    }

    void "when user try to remove a variant not associated with an activity it returns not found"() {
        given: "2 activities and some variants"
        def activityId = createActivity()
        createActivityVariant(activityId)
        createActivityVariant(activityId)
        def activityId2 = createActivity()
        def variantId = createActivityVariant(activityId2)

        when: "user remove variant 1 which is not associated to activity 1"
        def response = activityVariantClient.delete(new DeleteActivityVariantCommand(activityId, variantId))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "when user try to remove a variant not associated from an activity which does not exist it returns not found"() {
        given: "an unknown activity id and a variant from another activity"
        def activityId = UUID.randomUUID()
        def activityId2 = createActivity()
        def variantId = createActivityVariant(activityId2)

        when: "user remove a variant which is not associated, and from an activity which doesn't exist"
        def response = activityVariantClient.delete(new DeleteActivityVariantCommand(activityId, variantId))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "delete requires both an activity id and variant id"() {
        when: "delete variant associated and activity with invalid input"
        activityVariantClient.delete(new DeleteActivityVariantCommand(activityId, variantId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | variantId
        null              | null
        UUID.randomUUID() | null
        null              | UUID.randomUUID()
    }

}
