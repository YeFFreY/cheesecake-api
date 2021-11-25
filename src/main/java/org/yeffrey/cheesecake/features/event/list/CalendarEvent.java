package org.yeffrey.cheesecake.features.event.list;

import java.time.OffsetDateTime;

public record CalendarEvent(OffsetDateTime start, OffsetDateTime end, String description, String type) {
}
