package org.yeffrey.cheesecake.features.course.create;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CourseDomain(UUID id, UUID calendarId, UUID classId, OffsetDateTime start, OffsetDateTime end) {
}
