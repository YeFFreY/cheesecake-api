package org.yeffrey.cheesecake.features.calendar.create;

import java.util.UUID;

public record CalendarDomain(UUID id, UUID ownerId, String name, String description) {
}
