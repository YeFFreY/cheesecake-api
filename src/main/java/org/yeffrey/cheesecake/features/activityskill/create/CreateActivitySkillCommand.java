package org.yeffrey.cheesecake.features.activityskill.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record CreateActivitySkillCommand(@NotNull UUID activityId, @NotNull UUID skillId) {
}
