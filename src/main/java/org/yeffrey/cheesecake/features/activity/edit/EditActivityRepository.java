package org.yeffrey.cheesecake.features.activity.edit;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.exists;
import static org.jooq.impl.DSL.select;
import static org.yeffrey.cheesecake.Tables.T_ARTIFACT;
import static org.yeffrey.cheesecake.Tables.T_INVENTORY_ITEM;

@Singleton
public class EditActivityRepository {
    private final DSLContext ctx;

    public EditActivityRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public Optional<EditActivityCommand> edit(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_ARTIFACT.ID.eq(activityId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchOptionalInto(EditActivityCommand.class);
    }

    public boolean update(UUID activityId, UUID userId, String name, String description) {
        return ctx.update(T_ARTIFACT)
                .set(T_ARTIFACT.NAME, name)
                .set(T_ARTIFACT.DESCRIPTION, description)
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_ARTIFACT.ID.eq(activityId))
                .and(exists(select(T_INVENTORY_ITEM.ARTIFACT_ID).from(T_INVENTORY_ITEM).where(T_INVENTORY_ITEM.ARTIFACT_ID.eq(activityId).and(T_INVENTORY_ITEM.PARTY_ID.eq(userId)))))
                .execute() == 1;
    }
}
