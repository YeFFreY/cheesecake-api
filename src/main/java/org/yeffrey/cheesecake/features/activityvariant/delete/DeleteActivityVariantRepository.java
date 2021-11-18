package org.yeffrey.cheesecake.features.activityvariant.delete;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_VARIANT;

@Singleton
public class DeleteActivityVariantRepository {
    private final DSLContext ctx;

    public DeleteActivityVariantRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean delete(UUID activityId, UUID variantId) {
        return ctx.delete(T_VARIANT)
                .where(T_VARIANT.ACTIVITY_ID.eq(activityId))
                .and(T_VARIANT.ID.eq(variantId))
                .execute() == 1;
    }
}
