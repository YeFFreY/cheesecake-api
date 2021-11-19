package org.yeffrey.cheesecake.features.classs.create;

import java.util.UUID;

public record ClassDomain(UUID id, UUID ownerId, String name, String description) {
}
