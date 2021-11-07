package org.yeffrey.cheesecake.features.skill.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.skill.fixtures.SkillsFixtures
import org.yeffrey.cheesecake.features.skills.create.CreateSkillCommand

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateSkillControllerTest extends CheesecakeSpecification implements SkillsFixtures {

    void "user creates a new skill"() {
        given: "valid details of a new skill"
        def command = new CreateSkillCommand(skillTitle, skillDescription)

        when: "creates skill"
        var response = skillClient.create(command)

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        skillTitle = faker.lorem().sentence()
        skillDescription = faker.lorem().sentence()
    }

    void "new skill requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateSkillCommand(skillTitle, skillDescription)

        when: "creates skill"
        skillClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        skillTitle = ""
        skillDescription = ""
    }

}
