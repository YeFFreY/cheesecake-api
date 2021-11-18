package org.yeffrey.cheesecake.features.activityoperation.delete;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record DeleteActivityOperationCommand(@NotNull UUID activityId, @NotBlank String operationTypeId) {
}
