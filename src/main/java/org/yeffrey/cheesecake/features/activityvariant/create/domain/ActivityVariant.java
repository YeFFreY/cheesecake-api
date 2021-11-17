package org.yeffrey.cheesecake.features.activityvariant.create.domain;

import java.util.UUID;

public record ActivityVariant(UUID id, UUID activityId, String name, String description) {
}
