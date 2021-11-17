package org.yeffrey.cheesecake.features.activityvariant.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.yeffrey.cheesecake.features.activityvariant.create.domain.ActivityVariant;

import static org.yeffrey.cheesecake.Tables.T_VARIANT;

@Singleton
public class CreateActivityVariantRepository {
    private final DSLContext ctx;

    public CreateActivityVariantRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean create(ActivityVariant variant) {
        return ctx
                .insertInto(T_VARIANT, T_VARIANT.ID, T_VARIANT.ACTIVITY_ID, T_VARIANT.NAME, T_VARIANT.DESCRIPTION)
                .values(variant.id(), variant.activityId(), variant.name(), variant.description())
                .execute() == 1;
    }
}
