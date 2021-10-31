package org.yeffrey.cheesecake.features.activity.list.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.yeffrey.cheesecake.core.infra.domain.Identifiable;

import java.util.UUID;

public record ActivityOverview(UUID id, String name, String description) implements Identifiable {

    @JsonCreator
    public static ActivityOverview from(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("description") String description) {
        return new ActivityOverview(id, name, description);
    }

}
