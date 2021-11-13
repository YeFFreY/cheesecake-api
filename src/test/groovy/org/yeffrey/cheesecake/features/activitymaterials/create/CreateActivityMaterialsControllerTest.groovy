package org.yeffrey.cheesecake.features.activitymaterials.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activitymaterials.fixtures.ActivityMaterialsFixtures
import org.yeffrey.cheesecake.features.equipment.fixtures.EquipmentsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateActivityMaterialsControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, EquipmentsFixtures, ActivityMaterialsFixtures {

    void "user associate an equipment to an activity"() {
        given: "an activity and an equipment"
        def activityId = createActivity()
        def equipmentId = createEquipment()

        when: "associate equipment to activity"
        var response = activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        then:
        response.status == HttpStatus.CREATED
    }

    void "user cannot associate the same equipment multiple times to the same activity"() {
        given: "an activity and an equipment already associated"
        def activityId = createActivity()
        def equipmentId = createEquipment()
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        when: "associate again the same equipment to the same activity"
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.UNPROCESSABLE_ENTITY
    }

    void "association requires both an activity id and equipment id"() {

        when: "associate an equipment to activity"
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | equipmentId
        null              | null
        UUID.randomUUID() | null
        null              | UUID.randomUUID()
    }

    void "user cannot associate an equipment to an unknown activity"() {
        given: "an unknown activity and an equipment"
        def activityId = UUID.randomUUID()
        def equipmentId = createEquipment()

        when:
        def response = activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user cannot associate an invalid equipment to an activity"() {
        given: "an activity and an unknown equipment"
        def activityId = createActivity()
        def equipmentId = UUID.randomUUID()

        when:
        def response = activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user retrieves the list of equipments he did not yet associated with the activity"() {
        given: "2 activities"
        def activityId = createActivity()
        def activityId2 = createActivity()

        and: "some equipments"
        def equipmentId = createEquipment()
        def equipmentId2 = createEquipment()
        def equipmentId3 = createEquipment()
        def equipmentId4 = createEquipment()

        and: "equipment 1 and 2 are associated with the first activity"
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId))
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId, equipmentId2))

        and: "equipment 1 and 3 is associated with the second activity"
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId2, equipmentId))
        activityMaterialsClient.create(new CreateActivityMaterialsCommand(activityId2, equipmentId3))

        when:
        def response = activityMaterialsClient.listAvailable(activityId)

        then:
        response.status == HttpStatus.OK

        then: "equipment 1 and 2 should NOT be available for the first activity"
        response.body().data().stream().noneMatch() {
            it.id() == equipmentId || it.id() == equipmentId2
        }

        then: "equipment 3 and 4 should be available for the first activity (among the list of equipments also created by previous test)"
        response.body().data().stream().anyMatch() {
            it.id() == equipmentId3 || it.id() == equipmentId4
        }
    }
}
