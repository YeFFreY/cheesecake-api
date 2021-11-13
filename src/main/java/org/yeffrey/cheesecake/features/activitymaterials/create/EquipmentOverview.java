package org.yeffrey.cheesecake.features.activitymaterials.create;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record EquipmentOverview(UUID id, String name, String description) implements Identifiable {
}
