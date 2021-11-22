package org.yeffrey.cheesecake.features.course.details;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/api/courses/{courseId}")
public class DetailsCourseController {

    private final DetailsCourseService service;

    public DetailsCourseController(DetailsCourseService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public Optional<QueryResult<CourseDetails>> details(@PathVariable UUID courseId) {
        return service.details(courseId).map(QueryResult::new);
    }

}
