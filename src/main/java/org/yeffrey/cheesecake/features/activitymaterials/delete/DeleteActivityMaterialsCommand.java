package org.yeffrey.cheesecake.features.activitymaterials.delete;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record DeleteActivityMaterialsCommand(@NotNull UUID activityId, @NotNull UUID equipmentId) {
}
