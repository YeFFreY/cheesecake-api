package org.yeffrey.cheesecake.features.equipment.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record CreateEquipmentCommand(@NotBlank String name, @NotBlank String description) {
}
