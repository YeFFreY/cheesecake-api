package org.yeffrey.cheesecake.features.activity.create.domain;

import java.util.UUID;

public record Activity(UUID id, UUID ownerId, String name, String description) {
}
