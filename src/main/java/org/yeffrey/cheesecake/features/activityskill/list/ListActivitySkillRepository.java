package org.yeffrey.cheesecake.features.activityskill.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListActivitySkillRepository {
    private final DSLContext ctx;

    public ListActivitySkillRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public List<ActivitySkill> list(UUID activityId, UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_SKILL).on(T_ARTIFACT.ID.eq(T_SKILL.ID))
                .innerJoin(T_ACTIVITY_BENEFIT).on(T_SKILL.ID.eq(T_ACTIVITY_BENEFIT.SKILL_ID).and(T_ACTIVITY_BENEFIT.ACTIVITY_ID.eq(activityId)))
                .where(T_ARTIFACT.TYPE_ID.eq("SKILL"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(ActivitySkill.class);
    }
}
