package org.yeffrey.cheesecake.features.activityskill.create;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record SkillOverview(UUID id, String name, String description) implements Identifiable {
}
