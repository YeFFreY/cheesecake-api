package org.yeffrey.cheesecake.features.course.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Introspected
public record CreateCourseCommand(@NotNull UUID calendarId, @NotNull UUID classId, @NotNull ZonedDateTime start,
                                  @NotNull ZonedDateTime end) {
}
