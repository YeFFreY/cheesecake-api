package org.yeffrey.cheesecake.features.courseactivity.create;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.CommandResult;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/api/course-activities")
public class CreateCourseActivityController {

    private final CreateCourseActivityService service;

    public CreateCourseActivityController(CreateCourseActivityService service) {
        this.service = service;
    }

    @Get("/types")
    public QueryResult<List<SectionType>> listAvailableTypes() {
        return new QueryResult<>(service.listAvailableTypes());
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Optional<CommandResult<UUID>> create(@Valid @Body CreateCourseActivityCommand command) {
        return service.create(command).map(CommandResult::new);
    }
}
