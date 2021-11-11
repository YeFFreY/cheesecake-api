package org.yeffrey.cheesecake.features.activityskill.create;

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
@Controller("/api/activity-skills")
public class CreateActivitySkillController {

    private final CreateActivitySkillService service;

    public CreateActivitySkillController(CreateActivitySkillService service) {
        this.service = service;
    }

    @Get("/available/{activityId}")
    public QueryResult<List<SkillOverview>> listAvailable(@PathVariable UUID activityId) {
        List<SkillOverview> a = service.listAvailable(activityId);
        return new QueryResult<>(a);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Optional<CommandResult<UUID>> createActivity(@Valid @Body CreateActivitySkillCommand command) {
        return service.create(command).map(CommandResult::new);
    }
}
