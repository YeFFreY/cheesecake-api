package org.yeffrey.cheesecake.features.activitymaterials.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListActivityMaterialsRepository {
    private final DSLContext ctx;

    public ListActivityMaterialsRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public List<ActivityMaterials> list(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_EQUIPMENT).on(T_ARTIFACT.ID.eq(T_EQUIPMENT.ID))
                .innerJoin(T_MATERIALS).on(T_EQUIPMENT.ID.eq(T_MATERIALS.EQUIPMENT_ID).and(T_MATERIALS.ACTIVITY_ID.eq(activityId)))
                .where(T_ARTIFACT.TYPE_ID.eq("EQUIPMENT"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(ActivityMaterials.class);
    }
}
