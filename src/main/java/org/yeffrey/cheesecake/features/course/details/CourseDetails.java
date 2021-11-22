package org.yeffrey.cheesecake.features.course.details;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CourseDetails(UUID id, ClassOverview classOverview, OffsetDateTime start,
                            OffsetDateTime end) implements Identifiable {
}
