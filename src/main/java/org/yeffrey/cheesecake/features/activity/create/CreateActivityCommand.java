package org.yeffrey.cheesecake.features.activity.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record CreateActivityCommand(@NotBlank String name, @NotBlank String description) {

}
