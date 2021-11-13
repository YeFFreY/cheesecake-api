package org.yeffrey.cheesecake.features.activitymaterials.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateActivityMaterialsRepository {
    private final DSLContext ctx;

    public CreateActivityMaterialsRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean create(UUID activityId, UUID equipmentId) {
        return ctx
                .insertInto(T_MATERIALS, T_MATERIALS.ACTIVITY_ID, T_MATERIALS.EQUIPMENT_ID, T_MATERIALS.QUANTITY)
                .values(activityId, equipmentId, 1)
                .execute() == 1;
    }

    public List<EquipmentOverview> listAvailable(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_EQUIPMENT).on(T_ARTIFACT.ID.eq(T_EQUIPMENT.ID))
                .leftJoin(T_MATERIALS).on(T_EQUIPMENT.ID.eq(T_MATERIALS.EQUIPMENT_ID).and(T_MATERIALS.ACTIVITY_ID.eq(activityId)))
                .where(T_ARTIFACT.TYPE_ID.eq("EQUIPMENT"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .and(T_MATERIALS.ACTIVITY_ID.isNull())
                .fetchInto(EquipmentOverview.class);
    }
}
