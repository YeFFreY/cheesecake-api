package org.yeffrey.cheesecake.features.skills.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record CreateSkillCommand(@NotBlank String name, @NotBlank String description) {
}
