package org.yeffrey.cheesecake.features.courseactivity.delete;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record DeleteCourseActivityCommand(@NotNull UUID courseId, @NotNull UUID activityId,
                                          @NotBlank String courseSectionTypeId) {
}
