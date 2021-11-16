package org.yeffrey.cheesecake.features.activityoperation.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListActivityOperationRepository {
    private final DSLContext ctx;

    public ListActivityOperationRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public List<ActivityOperation> list(UUID activityId, UUID userId) {
        return ctx.select(T_OPERATION.TYPE_ID, T_OPERATION_TYPE.DESCRIPTION, T_OPERATION.DESCRIPTION)
                .from(T_OPERATION)
                .innerJoin(T_ACTIVITY).on(T_ACTIVITY.ID.eq(T_OPERATION.ACTIVITY_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_ACTIVITY.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_OPERATION_TYPE).on(T_OPERATION_TYPE.ID.eq(T_OPERATION.TYPE_ID))
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_ACTIVITY.ID.eq(activityId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(ActivityOperation.class);
    }
}
