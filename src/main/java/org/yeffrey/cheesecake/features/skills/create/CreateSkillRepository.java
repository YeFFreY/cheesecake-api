package org.yeffrey.cheesecake.features.skills.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.yeffrey.cheesecake.features.skills.create.domain.Skill;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateSkillRepository {
    private final DSLContext ctx;

    public CreateSkillRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(Skill skill) {
        ctx
                .insertInto(T_ARTIFACT, T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION, T_ARTIFACT.TYPE_ID)
                .values(skill.id(), skill.name(), skill.description(), "SKILL")
                .execute();
        ctx
                .insertInto(T_SKILL, T_SKILL.ID)
                .values(skill.id())
                .execute();
        ctx
                .insertInto(T_INVENTORY_ITEM, T_INVENTORY_ITEM.ARTIFACT_ID, T_INVENTORY_ITEM.PARTY_ID, T_INVENTORY_ITEM.ACCESS_CONTROL_TYPE_ID)
                .values(skill.id(), skill.ownerId(), "AUTHOR")
                .execute();

    }
}
