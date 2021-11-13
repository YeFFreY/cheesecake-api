package org.yeffrey.cheesecake.features.activitymaterials.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record CreateActivityMaterialsCommand(@NotNull UUID activityId, @NotNull UUID equipmentId) {
}
