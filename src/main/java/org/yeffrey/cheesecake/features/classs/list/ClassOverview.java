package org.yeffrey.cheesecake.features.classs.list;

import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record ClassOverview(UUID id, String name, String description) implements Identifiable {
}
