package org.yeffrey.cheesecake.features.classs.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListClassControllerTest extends CheesecakeSpecification implements ClassFixtures {

    void "when user creates a class he can retrieve it"() {
        given: "bob has recorded a class"
        def classId = createClass()
        def classId2 = createClass()

        when: "bob request the list of his classes"
        var response = classClient.list()

        then:
        response.status == HttpStatus.OK

        then:
        def classes = response.body().data()
        classes*.id() == [classId, classId2]


    }
}
