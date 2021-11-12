package org.yeffrey.cheesecake.features.activityskill.delete;

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
@Controller("/api/activity-skills")
public class DeleteActivitySkillController {
    private final DeleteActivitySkillService service;

    public DeleteActivitySkillController(DeleteActivitySkillService service) {
        this.service = service;
    }

    @Post("/delete")
    @Status(HttpStatus.OK)
    public Optional<CommandResult<UUID>> delete(@Valid @Body DeleteActivitySkillCommand command) {
        return service.delete(command).map(CommandResult::new);
    }
}
