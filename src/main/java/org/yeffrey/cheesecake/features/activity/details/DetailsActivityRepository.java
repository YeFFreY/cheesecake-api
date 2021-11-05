package org.yeffrey.cheesecake.features.activity.details;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.yeffrey.cheesecake.features.activity.details.domain.ActivityDetails;

import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_ARTIFACT;
import static org.yeffrey.cheesecake.Tables.T_INVENTORY_ITEM;

@Singleton
public class DetailsActivityRepository {
    private final DSLContext ctx;

    public DetailsActivityRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public Optional<ActivityDetails> details(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_ARTIFACT.ID.eq(activityId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchOptionalInto(ActivityDetails.class);
    }
}
