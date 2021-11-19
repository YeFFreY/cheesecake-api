package org.yeffrey.cheesecake.features.classs.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_ARTIFACT;
import static org.yeffrey.cheesecake.Tables.T_INVENTORY_ITEM;

@Singleton
public class ListClassRepository {
    private final DSLContext ctx;

    public ListClassRepository(DSLContext ctx) {
        this.ctx = ctx;
    }


    public List<ClassOverview> list(UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CLASS"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(ClassOverview.class);
    }
}
