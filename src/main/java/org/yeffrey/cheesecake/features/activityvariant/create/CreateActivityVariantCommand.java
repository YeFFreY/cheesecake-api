package org.yeffrey.cheesecake.features.activityvariant.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record CreateActivityVariantCommand(@NotNull UUID activityId, @NotBlank String name,
                                           @NotBlank String description) {
}
