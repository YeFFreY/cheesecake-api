package org.yeffrey.cheesecake.features.course.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.features.course.create.CreateCourseCommand

import java.time.ZonedDateTime

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/courses")
interface CourseClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateCourseCommand command)

}

trait CourseFixtures {
    abstract Faker getFaker()

    @Inject
    CourseClient courseClient

    UUID createCourse(UUID calendarId, UUID classId) {
        return courseClient.create(new CreateCourseCommand(calendarId, classId, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1))).body().data()
    }

}