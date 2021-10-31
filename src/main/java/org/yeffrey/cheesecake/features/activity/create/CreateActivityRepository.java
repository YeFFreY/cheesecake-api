package org.yeffrey.cheesecake.features.activity.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.yeffrey.cheesecake.features.activity.create.domain.Activity;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateActivityRepository {

    private final DSLContext ctx;

    public CreateActivityRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(Activity activity) {
        ctx
                .insertInto(T_ARTIFACT, T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION, T_ARTIFACT.TYPE_ID)
                .values(activity.id(), activity.name(), activity.description(), "ACTIVITY")
                .execute();
        ctx
                .insertInto(T_ACTIVITY, T_ACTIVITY.ID)
                .values(activity.id())
                .execute();
        ctx
                .insertInto(T_INVENTORY_ITEM, T_INVENTORY_ITEM.ARTIFACT_ID, T_INVENTORY_ITEM.PARTY_ID, T_INVENTORY_ITEM.ACCESS_CONTROL_TYPE_ID)
                .values(activity.id(), activity.ownerId(), "AUTHOR")
                .execute();

    }
}
