package org.yeffrey.cheesecake.features.calendar.create;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public record CreateCalendarCommand(@NotBlank String name, @NotBlank String description) {
}
