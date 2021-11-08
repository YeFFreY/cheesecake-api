package org.yeffrey.cheesecake.features.skill.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.skill.fixtures.SkillsFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListSkillControllerTest extends CheesecakeSpecification implements SkillsFixtures {

    void "when user creates an skill he can retrieve it"() {
        given: "bob has recorded an skill"
        def skillId = createSkill(skillName, skillDescription)

        when: "bob request the list of his skills"
        var response = skillClient.list()

        then:
        response.status == HttpStatus.OK

        then:
        response.body().data().stream().anyMatch {
            it.id() == skillId
                    && it.name() == skillName
                    && it.description() == skillDescription
        }

        where:
        skillName = faker.lorem().sentence()
        skillDescription = faker.lorem().sentence()

    }
}
