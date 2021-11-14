package org.yeffrey.cheesecake.features.activitymaterials.delete;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_MATERIALS;

@Singleton
public class DeleteActivityMaterialsRepository {
    private final DSLContext ctx;

    public DeleteActivityMaterialsRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean delete(UUID activityId, UUID equipmentId) {
        return ctx.delete(T_MATERIALS)
                .where(T_MATERIALS.ACTIVITY_ID.eq(activityId))
                .and(T_MATERIALS.EQUIPMENT_ID.eq(equipmentId))
                .execute() == 1;
    }
}
