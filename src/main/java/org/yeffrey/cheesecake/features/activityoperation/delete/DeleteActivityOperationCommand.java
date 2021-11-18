package org.yeffrey.cheesecake.features.activityoperation.delete;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record DeleteActivityOperationCommand(@NotNull UUID activityId, @NotBlank String operationTypeId) {
}
