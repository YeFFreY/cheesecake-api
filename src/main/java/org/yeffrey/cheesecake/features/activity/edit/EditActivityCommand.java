package org.yeffrey.cheesecake.features.activity.edit;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record EditActivityCommand(@NotBlank String name, @NotBlank String description) {
}
