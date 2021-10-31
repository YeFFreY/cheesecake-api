package org.yeffrey.cheesecake.features.activity.create;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.CommandResult;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@Controller("/api/activities")
public class CreateActivityController {

    private final CreateActivityService service;

    public CreateActivityController(CreateActivityService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public CommandResult<UUID> createActivity(@Valid @Body CreateActivityCommand command) {
        return new CommandResult<>(service.create(command));
    }
}
