package org.yeffrey.cheesecake.features.courseactivity.list;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;
import java.util.UUID;

@Validated
@Controller("/api/course-activities")
public class ListCourseActivityController {
    private final ListCourseActivityService service;

    public ListCourseActivityController(ListCourseActivityService service) {
        this.service = service;
    }

    @Get("/{courseId}")
    public QueryResult<List<CourseActivity>> list(@PathVariable UUID courseId) {
        return new QueryResult<>(service.list(courseId));
    }
}
