package org.yeffrey.cheesecake.features.activity.details.domain;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record ActivityDetails(UUID id, String name, String description) implements Identifiable {
}
