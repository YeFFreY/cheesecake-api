package org.yeffrey.cheesecake.features.calendar.list;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record CalendarOverview(UUID id, String name, String description) implements Identifiable {
}
