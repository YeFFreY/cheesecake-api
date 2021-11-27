package org.yeffrey.cheesecake.features.courseactivity.list

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.yeffrey.cheesecake.CheesecakeSpecification
import org.yeffrey.cheesecake.features.activity.fixtures.ActivitiesFixtures
import org.yeffrey.cheesecake.features.calendar.fixtures.CalendarFixtures
import org.yeffrey.cheesecake.features.classs.fixtures.ClassFixtures
import org.yeffrey.cheesecake.features.course.fixtures.CourseFixtures
import org.yeffrey.cheesecake.features.courseactivity.fixtures.CourseActivityFixtures

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class ListCourseActivityControllerTest extends CheesecakeSpecification implements ClassFixtures, CalendarFixtures, ActivitiesFixtures, CourseFixtures, CourseActivityFixtures {

    void "user can retrieve the list of activities associated with a given course"() {
        given: "2 courses and some activities associated"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)
        def courseId2 = createCourse(calendarId, classId)
        def activityId = createActivity()
        def activityId2 = createActivity()
        def activityId3 = createActivity()
        def activityId4 = createActivity()
        createCourseActivity(courseId, activityId)
        createCourseActivity(courseId, activityId2)
        createCourseActivity(courseId, activityId3, "COOL_DOWN")
        createCourseActivity(courseId2, activityId2)
        createCourseActivity(courseId2, activityId4)

        when:
        def response = courseActivityClient.list(courseId)

        then:
        response.status == HttpStatus.OK

        then:
        def courseActivities = response.body().data()
        courseActivities*.activityId().sort() == [activityId, activityId2, activityId3].sort()

    }

    void "when no activities associated to course, get an empty list"() {
        given: "a course without activities"
        def classId = createClass()
        def calendarId = createCalendar()
        def courseId = createCourse(calendarId, classId)

        when:
        def response = courseActivityClient.list(courseId)

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }

    void "when retrieving activities from unknown course, get empty result"() {
        when:
        def response = courseActivityClient.list(UUID.randomUUID())

        then:
        response.status() == HttpStatus.OK

        then:
        response.body().data().size() == 0
    }
}
