package org.yeffrey.cheesecake.features.activitymaterials.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activitymaterials.fixtures.ActivityMaterialsFixtures
import org.yeffrey.cheesecake.features.equipment.fixtures.EquipmentsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListActivityMaterialsControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, EquipmentsFixtures, ActivityMaterialsFixtures {

    void "user can retrieve the list of equipments associated with a given activty"() {
        given: "an activity and some equipments"
        def activityId = createActivity()
        def equipmentId = createEquipment()
        def equipmentId2 = createEquipment()
        def equipmentId3 = createEquipment()
        createEquipment()

        and: "the activity is associated with equipment 1, 2 and 3"
        createActivityMaterials(activityId, equipmentId)
        createActivityMaterials(activityId, equipmentId2)
        createActivityMaterials(activityId, equipmentId3)

        when:
        def response = activityMaterialsClient.list(activityId)

        then:
        response.status == HttpStatus.OK

        then:
        def equipments = response.body().data()
        equipments*.id() == [equipmentId, equipmentId2, equipmentId3]

    }

    void "when no equipments associated to activity, get an empty list"() {
        given: "an activity and some equipments but activity is associated to none"
        def activityId = createActivity()
        createEquipment()
        createEquipment()
        createEquipment()

        when:
        def response = activityMaterialsClient.list(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }

    void "when retrieving materials from unknown activity, get empty result"() {
        when:
        def response = activityMaterialsClient.list(UUID.randomUUID())

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }
}
