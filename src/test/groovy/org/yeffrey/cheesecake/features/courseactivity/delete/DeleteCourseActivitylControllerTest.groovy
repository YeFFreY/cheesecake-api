package org.yeffrey.cheesecake.features.courseactivity.delete

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
class DeleteCourseActivitylControllerTest extends CheesecakeSpecification implements ClassFixtures, CalendarFixtures, ActivitiesFixtures, CourseFixtures, CourseActivityFixtures {
    void "user is able to remove an associated activity from the list of activities of a course"() {
        given: "a course and some activities associated with it"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def activityId = createActivity()
        def activityId2 = createActivity()
        def activityId3 = createActivity()
        createCourseActivity(courseId, activityId)
        createCourseActivity(courseId, activityId2)
        createCourseActivity(courseId, activityId3, "COOL_DOWN")

        when:
        def response = courseActivityClient.delete(new DeleteCourseActivityCommand(courseId, activityId3, "COOL_DOWN"))

        then:
        response.status == HttpStatus.OK

        then:
        def activities = listCourseActivities(courseId)
        activities.size() == 2
        activities*.activityId() == [activityId, activityId2]

    }

    void "when user try to remove an activity not associated with the course it returns not found"() {
        given: "a course and some activities associated with it"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def activityId = createActivity()
        def activityId2 = createActivity()
        def activityId3 = createActivity()
        createCourseActivity(courseId, activityId)
        createCourseActivity(courseId, activityId2)

        when: "user remove activity 3 which is not associated"
        def response = courseActivityClient.delete(new DeleteCourseActivityCommand(courseId, activityId3, "COOL_DOWN"))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "when user try to remove an activity not associated from a course which does not exist it returns not found"() {
        given: "an activity and some skills"
        def courseId = UUID.randomUUID()
        def activityId = createActivity()

        when: "user remove activity which is not associated, and from a course which doesn't exist"
        def response = courseActivityClient.delete(new DeleteCourseActivityCommand(courseId, activityId, "COOL_DOWN"))

        then:
        response.status == HttpStatus.NOT_FOUND

    }

    void "delete requires both an course id, activity id and course section id"() {

        when: "delete association between activity and course"
        courseActivityClient.delete(new DeleteCourseActivityCommand(courseId, activityId, sectionTypeId))

        then:
        HttpClientResponseException error = thrown()

        then:
        error.status == HttpStatus.BAD_REQUEST

        where:
        courseId          | activityId        | sectionTypeId
        null              | null              | null
        UUID.randomUUID() | null              | null
        null              | UUID.randomUUID() | null
        null              | null              | "COOL_DOWN"
        null              | UUID.randomUUID() | "COOL_DOWN"
        UUID.randomUUID() | UUID.randomUUID() | null
        UUID.randomUUID() | null              | "COOL_DOWN"
    }

}
