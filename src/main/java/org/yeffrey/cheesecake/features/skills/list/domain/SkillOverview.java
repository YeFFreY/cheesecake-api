package org.yeffrey.cheesecake.features.skills.list.domain;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record SkillOverview(UUID id, String name, String description) implements Identifiable {
}
