package org.yeffrey.cheesecake.features.activitymaterials.delete;

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
@Controller("/api/activity-materials")
public class DeleteActivityMaterialsController {
    private final DeleteActivityMaterialsService service;

    public DeleteActivityMaterialsController(DeleteActivityMaterialsService service) {
        this.service = service;
    }

    @Post("/delete")
    @Status(HttpStatus.OK)
    public Optional<CommandResult<UUID>> delete(@Valid @Body DeleteActivityMaterialsCommand command) {
        return service.delete(command).map(CommandResult::new);
    }
}
