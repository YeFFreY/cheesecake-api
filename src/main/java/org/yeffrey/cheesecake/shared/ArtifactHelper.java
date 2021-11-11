package org.yeffrey.cheesecake.shared;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_ARTIFACT;
import static org.yeffrey.cheesecake.Tables.T_INVENTORY_ITEM;

@Singleton
public class ArtifactHelper {
    private final DSLContext ctx;

    public ArtifactHelper(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean artifactExists(UUID artifactId, String artifactType, UUID userId) {
        Integer count = ctx.selectCount()
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq(artifactType))
                .and(T_ARTIFACT.ID.eq(artifactId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchOne(0, int.class);
        return Optional.ofNullable(count).map(c -> c == 1).orElse(false);
    }
}
