package org.yeffrey.cheesecake.features.skills.create.domain;

import java.util.UUID;

public record Skill(UUID id, UUID ownerId, String name, String description) {
}
