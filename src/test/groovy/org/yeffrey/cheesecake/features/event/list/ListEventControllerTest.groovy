package org.yeffrey.cheesecake.features.event.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures
import org.yeffrey.cheesecake.features.course.fixtures.CourseFixtures
import org.yeffrey.cheesecake.features.event.fixtures.EventFixtures

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListEventControllerTest extends CheesecakeSpecification implements ClassFixtures, EventFixtures, CourseFixtures, CalendarFixtures {

    void "when user creates some events (here courses) he can retrieve it as they belong to same week"() {
        given: "bob has recorded a some courses in a calendar"
        def calendarId = createCalendar()
        def calendarId2 = createCalendar()
        def classId = createClass(className1)
        def classId2 = createClass(className2)
        def classId3 = createClass()

        createCourse(calendarId, classId, start1, start1.plusHours(1))
        createCourse(calendarId, classId2, start2, start2.plusHours(1))
        createCourse(calendarId, classId2, start2, start2.plusDays(7))
        createCourse(calendarId2, classId3, start1, start1.plusHours(1))
        createCourse(calendarId2, classId3, start2, start2.plusHours(1))

        when: "bob request the list of his classes"
        var response = eventClient.list(calendarId, OffsetDateTime.now().toString())

        then:
        response.status == HttpStatus.OK

        then:
        def events = response.body().data()
        events.size() == 2
        events*.description() == [className1, className2]
        events*.start() == [start1, start2]


        where:
        className1 = faker.lorem().sentence()
        className2 = faker.lorem().sentence()
        start1 = OffsetDateTime.of(LocalDateTime.of(2021, 11, 25, 0, 0, 0), ZoneOffset.ofHours(0))
        start2 = OffsetDateTime.of(LocalDateTime.of(2021, 11, 25, 0, 0, 0), ZoneOffset.ofHours(0))

    }

    void "events outside the week related to the given date are not returned"() {
        given: "bob has recorded a some courses in a calendar for next week"
        def calendarId = createCalendar()
        def classId = createClass()

        createCourse(calendarId, classId, start, start.plusHours(1))
        createCourse(calendarId, classId, start.plusDays(1), start.plusDays(1).plusHours(1))
        createCourse(calendarId, classId, start.plusDays(1), start.plusDays(1).plusHours(1))

        when: "bob request the list of his classes for this week"
        var response = eventClient.list(calendarId, OffsetDateTime.now().toString())

        then:
        response.status == HttpStatus.OK

        then:
        def events = response.body().data()
        events.size() == 0

        where:
        start = OffsetDateTime.now().plusDays(8)

    }
}
