package org.yeffrey.cheesecake.features.activitymaterials.delete

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
class DeleteActivityMaterialsControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, EquipmentsFixtures, ActivityMaterialsFixtures {
    void "user is able to remove an associated equipment from the list of equipments of an activity"() {
        given: "an activity and some equipments"
        def activityId = createActivity()
        def equipmentId = createEquipment()
        def equipmentId2 = createEquipment()
        createEquipment()

        and: "activity is associated with equipment 1 and 2"
        createActivityMaterials(activityId, equipmentId)
        createActivityMaterials(activityId, equipmentId2)

        when:
        def response = activityMaterialsClient.delete(new DeleteActivityMaterialsCommand(activityId, equipmentId2))

        then:
        response.status == HttpStatus.OK

        then:
        def equipments = listActivityMaterials(activityId)
        equipments.size() == 1
        equipments*.id() == [equipmentId]

    }

    void "when user try to remove a equipment not associated with an activity it returns not found"() {
        given: "an activity and some equipments"
        def activityId = createActivity()
        def equipmentId = createEquipment()
        def equipmentId2 = createEquipment()
        createEquipment()

        and: "activity is associated with equipment 1"
        createActivityMaterials(activityId, equipmentId)

        when: "user remove equipment 2 which is not associated"
        def response = activityMaterialsClient.delete(new DeleteActivityMaterialsCommand(activityId, equipmentId2))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "when user try to remove a equipment not associated from an activity which does not exist it returns not found"() {
        given: "an activity and some equipments"
        def activityId = UUID.randomUUID()
        def equipmentId = createEquipment()

        when: "user remove equipment which is not associated, and from an activity which doesn't exist"
        def response = activityMaterialsClient.delete(new DeleteActivityMaterialsCommand(activityId, equipmentId))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "delete requires both an activity id and equipment id"() {

        when: "delete association between equipment and activity"
        activityMaterialsClient.delete(new DeleteActivityMaterialsCommand(activityId, equipmentId))

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

}
