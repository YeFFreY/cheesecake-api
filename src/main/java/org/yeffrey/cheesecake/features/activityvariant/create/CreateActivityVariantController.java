package org.yeffrey.cheesecake.features.activityvariant.create;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.CommandResult;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/api/activity-variants")
public class CreateActivityVariantController {

    private final CreateActivityVariantService service;

    public CreateActivityVariantController(CreateActivityVariantService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Optional<CommandResult<UUID>> create(@Valid @Body CreateActivityVariantCommand command) {
        return service.create(command).map(CommandResult::new);
    }
}
