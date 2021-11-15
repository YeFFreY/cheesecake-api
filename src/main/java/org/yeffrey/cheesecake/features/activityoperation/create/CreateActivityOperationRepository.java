package org.yeffrey.cheesecake.features.activityoperation.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_OPERATION;
import static org.yeffrey.cheesecake.Tables.T_OPERATION_TYPE;

@Singleton
public class CreateActivityOperationRepository {
    private final DSLContext ctx;

    public CreateActivityOperationRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean create(UUID activityId, String type, String description) {
        return ctx
                .insertInto(T_OPERATION, T_OPERATION.ACTIVITY_ID, T_OPERATION.TYPE_ID, T_OPERATION.DESCRIPTION)
                .values(activityId, type, description)
                .execute() == 1;
    }

    public boolean operationTypeExists(String type) {
        Integer count = ctx.selectCount().from(T_OPERATION_TYPE).where(T_OPERATION_TYPE.ID.eq(type)).fetchOne(0, int.class);
        return Optional.ofNullable(count).map(c -> c == 1).orElse(false);
    }

    public List<OperationType> listAvailableTypes(UUID activityId) {
        return ctx.select(T_OPERATION_TYPE.ID, T_OPERATION_TYPE.DESCRIPTION)
                .from(T_OPERATION_TYPE)
                .leftJoin(T_OPERATION).on(T_OPERATION_TYPE.ID.eq(T_OPERATION.TYPE_ID).and(T_OPERATION.ACTIVITY_ID.eq(activityId)))
                .where(T_OPERATION.ACTIVITY_ID.isNull())
                .fetchInto(OperationType.class);
    }
}
