package org.yeffrey.cheesecake.features.courseactivity.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.courseactivity.create.CreateCourseActivityCommand
import org.yeffrey.cheesecake.features.courseactivity.delete.DeleteCourseActivityCommand
import org.yeffrey.cheesecake.features.courseactivity.list.CourseActivity

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/course-activities")
interface CourseActivityClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateCourseActivityCommand command)


    @Get('/{courseId}')
    HttpResponse<QueryResult<List<CourseActivity>>> list(UUID courseId)


    @Post('/delete')
    HttpResponse<CommandResult<UUID>> delete(@Body DeleteCourseActivityCommand command)
}

trait CourseActivityFixtures {
    abstract Faker getFaker()

    @Inject
    CourseActivityClient courseActivityClient


    HttpStatus createCourseActivity(UUID courseId, UUID activityId, String type = "WARM_UP") {
        return courseActivityClient.create(new CreateCourseActivityCommand(courseId, activityId, type)).status()
    }


    List<CourseActivity> listCourseActivities(UUID courseId) {
        return courseActivityClient.list(courseId).body().data()
    }
}