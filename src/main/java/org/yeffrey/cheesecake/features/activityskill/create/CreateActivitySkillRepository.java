package org.yeffrey.cheesecake.features.activityskill.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateActivitySkillRepository {
    private final DSLContext ctx;

    public CreateActivitySkillRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean create(UUID activityId, UUID skillId) {
        return ctx
                .insertInto(T_ACTIVITY_BENEFIT, T_ACTIVITY_BENEFIT.ACTIVITY_ID, T_ACTIVITY_BENEFIT.SKILL_ID, T_ACTIVITY_BENEFIT.LEVEL)
                .values(activityId, skillId, 0)
                .execute() == 1;
    }

    public List<SkillOverview> listAvailable(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_SKILL).on(T_ARTIFACT.ID.eq(T_SKILL.ID))
                .leftJoin(T_ACTIVITY_BENEFIT).on(T_SKILL.ID.eq(T_ACTIVITY_BENEFIT.SKILL_ID).and(T_ACTIVITY_BENEFIT.ACTIVITY_ID.eq(activityId)))
                .where(T_ARTIFACT.TYPE_ID.eq("SKILL"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .and(T_ACTIVITY_BENEFIT.ACTIVITY_ID.isNull())
                .fetchInto(SkillOverview.class);
    }
}
