package org.yeffrey.cheesecake.features.course.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.course.create.CreateCourseCommand
import org.yeffrey.cheesecake.features.course.details.CourseDetails

import java.time.OffsetDateTime

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/courses")
interface CourseClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateCourseCommand command)

    @Get("/{courseId}")
    HttpResponse<QueryResult<CourseDetails>> details(@PathVariable UUID courseId)
}

trait CourseFixtures {
    abstract Faker getFaker()

    @Inject
    CourseClient courseClient

    UUID createCourse(UUID calendarId, UUID classId, OffsetDateTime start = OffsetDateTime.now(), OffsetDateTime end = OffsetDateTime.now().plusHours(1)) {
        return courseClient.create(new CreateCourseCommand(calendarId, classId, start, end)).body().data()
    }

}