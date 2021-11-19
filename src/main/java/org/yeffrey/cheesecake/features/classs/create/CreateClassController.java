package org.yeffrey.cheesecake.features.classs.create;

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
@Controller("/api/classes")
public class CreateClassController {

    private final CreateClassService service;

    public CreateClassController(CreateClassService service) {
        this.service = service;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public CommandResult<UUID> createClass(@Valid @Body CreateClassCommand command) {
        return new CommandResult<>(service.create(command));
    }
}
