package org.yeffrey.cheesecake.features.calendar.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateCalendarControllerTest extends CheesecakeSpecification implements CalendarFixtures {

    void "user creates a new calendar"() {
        given: "valid details of a new calendar"
        def command = new CreateCalendarCommand(calendarName, calendarDescription)

        when: "creates calendar"
        var response = calendarClient.create(command)

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        calendarName = faker.lorem().sentence()
        calendarDescription = faker.lorem().sentence()
    }

    void "new calendar requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateCalendarCommand(calendarTitle, calendarDescription)

        when: "creates calendar"
        calendarClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        calendarTitle = ""
        calendarDescription = ""
    }

}
