package org.yeffrey.cheesecake.features.equipment.create;

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
@Controller("/api/equipments")
public class CreateEquipmentController {

    private final CreateEquipmentService service;

    public CreateEquipmentController(CreateEquipmentService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public CommandResult<UUID> createSkill(@Valid @Body CreateEquipmentCommand command) {
        return new CommandResult<>(service.create(command));
    }

}
