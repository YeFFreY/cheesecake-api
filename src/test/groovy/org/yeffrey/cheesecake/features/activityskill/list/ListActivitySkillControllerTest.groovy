package org.yeffrey.cheesecake.features.activityskill.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityskill.fixtures.ActivitySkillsFixtures
import org.yeffrey.cheesecake.features.skill.fixtures.SkillsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListActivitySkillControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, SkillsFixtures, ActivitySkillsFixtures {

    void "user can retrieve the list of skills associated with a given activty"() {
        given: "an activity and some skills"
        def activityId = createActivity()
        def skillId = createSkill()
        def skillId2 = createSkill()
        def skillId3 = createSkill()
        createSkill()

        and: "the activity is associated with skill 1, 2 and 3"
        createActivitySkill(activityId, skillId)
        createActivitySkill(activityId, skillId2)
        createActivitySkill(activityId, skillId3)

        when:
        def response = activitySkillClient.list(activityId)

        then:
        response.status == HttpStatus.OK

        then:
        def skills = response.body().data()
        skills*.id() == [skillId, skillId2, skillId3]

    }

    void "when no skills associated to activity, get an empty list"() {
        given: "an activity and some skills but activity is associated to none"
        def activityId = createActivity()
        createSkill()
        createSkill()
        createSkill()

        when:
        def response = activitySkillClient.list(activityId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }

    void "when retrieving skills from unknown activity, get empty result"() {
        when:
        def response = activitySkillClient.list(UUID.randomUUID())

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }
}
