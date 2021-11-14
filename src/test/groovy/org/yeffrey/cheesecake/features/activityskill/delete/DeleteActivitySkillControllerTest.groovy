package org.yeffrey.cheesecake.features.activityskill.delete

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.activityskill.fixtures.ActivitySkillsFixtures
import org.yeffrey.cheesecake.features.skill.fixtures.SkillsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class DeleteActivitySkillControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, SkillsFixtures, ActivitySkillsFixtures {
    void "user is able to remove an associated skill from the list of skills of an activity"() {
        given: "an activity and some skills"
        def activityId = createActivity()
        def skillId = createSkill()
        def skillId2 = createSkill()
        createSkill()

        and: "activity is associated with skill 1 and 2"
        createActivitySkill(activityId, skillId)
        createActivitySkill(activityId, skillId2)

        when:
        def response = activitySkillClient.delete(new DeleteActivitySkillCommand(activityId, skillId2))

        then:
        response.status == HttpStatus.OK

        then:
        def skills = listActivitySkills(activityId)
        skills.size() == 1
        skills*.id() == [skillId]

    }

    void "when user try to remove a skill not associated with an activity it returns not found"() {
        given: "an activity and some skills"
        def activityId = createActivity()
        def skillId = createSkill()
        def skillId2 = createSkill()
        createSkill()

        and: "activity is associated with skill 1"
        createActivitySkill(activityId, skillId)

        when: "user remove skill 2 which is not associated"
        def response = activitySkillClient.delete(new DeleteActivitySkillCommand(activityId, skillId2))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "when user try to remove a skill not associated from an activity which does not exist it returns not found"() {
        given: "an activity and some skills"
        def activityId = UUID.randomUUID()
        def skillId = createSkill()

        when: "user remove skill which is not associated, and from an activity which doesn't exist"
        def response = activitySkillClient.delete(new DeleteActivitySkillCommand(activityId, skillId))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "delete requires both an activity id and skill id"() {

        when: "delete association between skill and activity"
        activitySkillClient.delete(new DeleteActivitySkillCommand(activityId, skillId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        activityId        | skillId
        null              | null
        UUID.randomUUID() | null
        null              | UUID.randomUUID()
    }

}
