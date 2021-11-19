package org.yeffrey.cheesecake.features.course.create;

import java.time.ZonedDateTime;
import java.util.UUID;

public record CourseDomain(UUID id, UUID calendarId, UUID classId, ZonedDateTime start, ZonedDateTime end) {
}
