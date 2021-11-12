package org.yeffrey.cheesecake.features.activityskill.delete;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_ACTIVITY_BENEFIT;

@Singleton
public class DeleteActivitySkillRepository {
    private final DSLContext ctx;

    public DeleteActivitySkillRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean delete(UUID activityId, UUID skillId) {
        return ctx.delete(T_ACTIVITY_BENEFIT)
                .where(T_ACTIVITY_BENEFIT.ACTIVITY_ID.eq(activityId))
                .and(T_ACTIVITY_BENEFIT.SKILL_ID.eq(skillId))
                .execute() == 1;
    }
}
