package org.yeffrey.cheesecake.features.courseactivity.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.features.courseactivity.create.CreateCourseActivityCommand

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/course-activities")
interface CourseActivityClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateCourseActivityCommand command)


}

trait CourseActivityFixtures {
    abstract Faker getFaker()

    @Inject
    CourseActivityClient courseActivityClient
}