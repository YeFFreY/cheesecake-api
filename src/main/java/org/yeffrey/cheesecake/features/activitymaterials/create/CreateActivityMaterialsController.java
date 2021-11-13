package org.yeffrey.cheesecake.features.activitymaterials.create;

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
@Controller("/api/activity-materials")
public class CreateActivityMaterialsController {

    private final CreateActivityMaterialsService service;

    public CreateActivityMaterialsController(CreateActivityMaterialsService service) {
        this.service = service;
    }

    @Get("/available/{activityId}")
    public QueryResult<List<EquipmentOverview>> listAvailable(@PathVariable UUID activityId) {
        return new QueryResult<>(service.listAvailable(activityId));
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Optional<CommandResult<UUID>> create(@Valid @Body CreateActivityMaterialsCommand command) {
        return service.create(command).map(CommandResult::new);
    }
}
