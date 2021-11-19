package org.yeffrey.cheesecake.features.calendar.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListCalendarControllerTest extends CheesecakeSpecification implements CalendarFixtures {

    void "when user creates a calendar he can retrieve it"() {
        given: "bob has recorded a calendar"
        def calendarId = createCalendar()
        def calendarId2 = createCalendar()

        when: "bob request the list of his calendars"
        var response = calendarClient.list()

        then:
        response.status == HttpStatus.OK

        then:
        def calendars = response.body().data()
        calendars*.id() == [calendarId, calendarId2]


    }
}
