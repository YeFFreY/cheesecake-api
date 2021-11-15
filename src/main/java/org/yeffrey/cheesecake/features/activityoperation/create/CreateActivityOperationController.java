package org.yeffrey.cheesecake.features.activityoperation.create;

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
@Controller("/api/activity-operations")
public class CreateActivityOperationController {

    private final CreateActivityOperationService service;

    public CreateActivityOperationController(CreateActivityOperationService service) {
        this.service = service;
    }

    @Get("/available/{activityId}")
    public QueryResult<List<OperationType>> listAvailableTypes(@PathVariable UUID activityId) {
        return new QueryResult<>(service.listAvailableTypes(activityId));
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Optional<CommandResult<UUID>> create(@Valid @Body CreateActivityOperationCommand command) {
        return service.create(command).map(CommandResult::new);
    }
}
