package org.yeffrey.cheesecake.features.course.details

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures
import org.yeffrey.cheesecake.features.course.fixtures.CourseFixtures

import java.time.OffsetDateTime
import java.time.ZoneId

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class DetailsCourseControllerTest extends CheesecakeSpecification implements ClassFixtures, CalendarFixtures, CourseFixtures {

    void "user can retrieve the details of a course he created"() {
        given: "a course"
        def classId = createClass(className)
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId, start, end)

        when: "bob request course details"
        def response = courseClient.details(courseId)

        then:
        response.status() == HttpStatus.OK

        then:
        def courseDetails = response.body().data()
        courseDetails.classOverview().id() == classId
        courseDetails.classOverview().name() == className
        courseDetails.start().format(dateTimeFormatter) == start.format(dateTimeFormatter)
        courseDetails.end().format(dateTimeFormatter) == end.format(dateTimeFormatter)

        where:
        className = faker.lorem().sentence()
        start = OffsetDateTime.now(ZoneId.of("UTC"))
        end = start.plusHours(1)
    }

    void "user receives a 404 when trying to retrieve a course which does not exists"() {
        given: "an id which does not exists"
        def courseID = UUID.randomUUID()

        when: "bob request course details"
        def response = courseClient.details(courseID)

        then:
        response.status() == HttpStatus.NOT_FOUND

        then:
        response.body() == null

    }
}
