package org.yeffrey.cheesecake.features.equipment.create.domain;

import java.util.UUID;

public record Equipment(UUID id, UUID ownerId, String name, String description) {
}
