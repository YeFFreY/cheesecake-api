package org.yeffrey.cheesecake.features.activityoperation.delete;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_OPERATION;

@Singleton
public class DeleteActivityOperationRepository {
    private final DSLContext ctx;

    public DeleteActivityOperationRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean delete(UUID activityId, String operationTypeId) {
        return ctx.delete(T_OPERATION)
                .where(T_OPERATION.ACTIVITY_ID.eq(activityId))
                .and(T_OPERATION.TYPE_ID.eq(operationTypeId))
                .execute() == 1;
    }
}
