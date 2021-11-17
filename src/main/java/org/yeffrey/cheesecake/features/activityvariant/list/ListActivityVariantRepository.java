package org.yeffrey.cheesecake.features.activityvariant.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListActivityVariantRepository {
    private final DSLContext ctx;

    public ListActivityVariantRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public List<ActivityVariant> list(UUID activityId, UUID userId) {
        return ctx.select(T_VARIANT.ID, T_VARIANT.NAME, T_VARIANT.DESCRIPTION)
                .from(T_VARIANT)
                .innerJoin(T_ACTIVITY).on(T_ACTIVITY.ID.eq(T_VARIANT.ACTIVITY_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_ACTIVITY.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_ACTIVITY.ID.eq(activityId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(ActivityVariant.class);
    }
}
