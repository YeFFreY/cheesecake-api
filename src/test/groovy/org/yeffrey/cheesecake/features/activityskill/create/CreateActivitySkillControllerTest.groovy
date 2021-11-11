package org.yeffrey.cheesecake.features.activityskill.create

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
class CreateActivitySkillControllerTest extends CheesecakeSpecification implements ActivitiesFixtures, SkillsFixtures, ActivitySkillsFixtures {

    void "user associate a skill to an activity"() {
        given: "an activity and a skill"
        def activityId = createActivity()
        def skillId = createSkill()

        when: "associate skill to activity"
        var response = activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))

        then:
        response.status == HttpStatus.CREATED
    }

    void "user cannot associate the same skill multiple times to the same activity"() {
        given: "an activity and a skill already associated"
        def activityId = createActivity()
        def skillId = createSkill()
        activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))

        when: "associate again the same skill to the same activity"
        activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.UNPROCESSABLE_ENTITY
    }

    void "association requires both an activity id and skill id"() {

        when: "associate skill to activity"
        activitySkillClient.create(new CreateActivitySkillCommand(null, null))

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

    void "user cannot associate a skill to an unknown activity"() {
        given: "an unknown activity and a skill"
        def activityId = UUID.randomUUID()
        def skillId = createSkill()

        when:
        def response = activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user cannot associate an invalid skill to an activity"() {
        given: "an activity and an unknown skill"
        def activityId = createActivity()
        def skillId = UUID.randomUUID()

        when:
        def response = activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user retrieves the list of skills he did not yet associated with the activity"() {
        given: "2 activities"
        def activityId = createActivity()
        def activityId2 = createActivity()

        and: "some skills"
        def skillId = createSkill()
        def skillId2 = createSkill()
        def skillId3 = createSkill()
        def skillId4 = createSkill()

        and: "skill 1 and 2 are associated with the first activity"
        activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId))
        activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId2))

        and: "skill 1 and 3 is associated with the second activity"
        activitySkillClient.create(new CreateActivitySkillCommand(activityId2, skillId))
        activitySkillClient.create(new CreateActivitySkillCommand(activityId2, skillId3))

        when:
        def response = activitySkillClient.listAvailable(activityId)

        then:
        response.status == HttpStatus.OK

        then: "skill 1 and 2 should NOT be available for the first activity"
        response.body().data().stream().noneMatch() {
            it.id() == skillId || it.id() == skillId2
        }

        then: "skill 3 and 4 should be available for the first activity (among the list of skills also created by previous test)"
        response.body().data().stream().anyMatch() {
            it.id() == skillId3 || it.id() == skillId4
        }
    }
}
