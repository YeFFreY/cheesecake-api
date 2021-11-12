package org.yeffrey.cheesecake.features.activityskill.delete;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record DeleteActivitySkillCommand(@NotNull UUID activityId, @NotNull UUID skillId) {
}
