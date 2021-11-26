package org.yeffrey.cheesecake.features.courseactivity.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record CreateCourseActivityCommand(@NotNull UUID courseId, @NotNull UUID activityId,
                                          @NotBlank String courseSectionTypeId) {
}
