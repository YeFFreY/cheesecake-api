package org.yeffrey.cheesecake.features.activityvariant.delete;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record DeleteActivityVariantCommand(@NotNull UUID activityId, @NotNull UUID variantId) {
}
