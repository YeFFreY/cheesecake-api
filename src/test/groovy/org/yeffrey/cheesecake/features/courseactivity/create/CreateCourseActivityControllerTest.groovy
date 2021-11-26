package org.yeffrey.cheesecake.features.courseactivity.create

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures
import org.yeffrey.cheesecake.features.course.fixtures.CourseFixtures
import org.yeffrey.cheesecake.features.courseactivity.fixtures.CourseActivityFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateCourseActivityControllerTest extends CheesecakeSpecification implements ClassFixtures, CalendarFixtures, ActivitiesFixtures, CourseFixtures, CourseActivityFixtures {

    void "user add an activity to a course"() {
        given: "a course"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def activityId = createActivity()


        when: "adding a variant to the activity"
        var response = courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, "WARM_UP"))

        then:
        response.status == HttpStatus.CREATED

        then:
        response.body().data() != null
    }

    void "course activity requires both a course id, an activity id and a type id"() {

        when: "associate an activity to a course"
        courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, typeId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        courseId          | activityId        | typeId
        UUID.randomUUID() | null              | null
        null              | UUID.randomUUID() | null
        null              | null              | "WARMUP"
        null              | null              | null
        UUID.randomUUID() | UUID.randomUUID() | null
        UUID.randomUUID() | null              | "WARMUP"
        null              | UUID.randomUUID() | faker.lorem().sentence()
    }

    void "user cannot associate an activity to an unknown course"() {
        given: "an unknown course"
        def activityId = createActivity()
        def courseId = UUID.randomUUID()

        when:
        def response = courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, "WARM_UP"))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user cannot associate an unknown activity to a course"() {
        given: "an unknown activity"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def activityId = UUID.randomUUID()

        when:
        def response = courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, "WARM_UP"))

        then:
        response.status == HttpStatus.NOT_FOUND
    }

    void "user cannot associate an activity to a course using an unknown type"() {
        given: "an unknown activity"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def activityId = createActivity()

        when:
        def response = courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, "UNKNOWN"))

        then:
        response.status == HttpStatus.NOT_FOUND
    }
}