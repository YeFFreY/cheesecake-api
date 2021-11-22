package org.yeffrey.cheesecake.features.course.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures
import org.yeffrey.cheesecake.features.course.fixtures.CourseFixtures

import java.time.OffsetDateTime

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateCourseControllerTest extends CheesecakeSpecification implements ClassFixtures, CalendarFixtures, CourseFixtures {

    void "user creates a new course"() {
        given: "a class and a calendar"
        def classId = createClass()
        def calendarId = createCalendar()

        when: "creates course"
        var response = courseClient.create(new CreateCourseCommand(calendarId, classId, start, end))

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null

        where:
        start = OffsetDateTime.now()
        end = OffsetDateTime.now().plusHours(1)
    }

    void "new course requires start and end date"() {
        given: "invalid command"
        def invalidCommand = new CreateCourseCommand(calendarId, classId, start, end)

        when: "creates class"
        courseClient.create(invalidCommand)

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        calendarId        | classId           | start                | end
        null              | null              | null                 | null
        UUID.randomUUID() | null              | null                 | null
        null              | UUID.randomUUID() | null                 | null
        null              | null              | OffsetDateTime.now() | null
        null              | null              | null                 | OffsetDateTime.now()

    }

    void "new course requires known calendar and class id"() {
        given: "invalid command"
        def invalidCommand = new CreateCourseCommand(UUID.randomUUID(), UUID.randomUUID(), OffsetDateTime.now(), OffsetDateTime.now())

        when: "creates class"
        def response = courseClient.create(invalidCommand)

        then:
        response.status == HttpStatus.NOT_FOUND


    }
}
