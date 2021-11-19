package org.yeffrey.cheesecake.features.classs.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record CreateClassCommand(@NotBlank String name, @NotBlank String description) {
}
