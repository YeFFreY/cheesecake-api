package org.yeffrey.cheesecake.features.activityoperation.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record CreateActivityOperationCommand(@NotNull UUID activityId, @NotBlank String type,
                                             @NotBlank String description) {
}
