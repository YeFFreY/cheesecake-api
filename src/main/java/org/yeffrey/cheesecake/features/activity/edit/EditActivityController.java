package org.yeffrey.cheesecake.features.activity.edit;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.CommandResult;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/api/activities/edit/{activityId}")
public class EditActivityController {

    private final EditActivityService service;

    public EditActivityController(EditActivityService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public Optional<QueryResult<EditActivityCommand>> edit(@PathVariable UUID activityId) {
        return service.edit(activityId).map(QueryResult::new);
    }

    @Post
    @Status(HttpStatus.OK)
    public Optional<CommandResult<UUID>> update(@PathVariable UUID activityId, @Valid @Body EditActivityCommand command) {
        return this.service.update(activityId, command).map(CommandResult::new);
    }
}
