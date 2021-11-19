package org.yeffrey.cheesecake.features.classs.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateClassRepository {

    private final DSLContext ctx;

    public CreateClassRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(ClassDomain classs) {
        ctx
                .insertInto(T_ARTIFACT, T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION, T_ARTIFACT.TYPE_ID)
                .values(classs.id(), classs.name(), classs.description(), "CLASS")
                .execute();
        ctx
                .insertInto(T_CLASS, T_CLASS.ID)
                .values(classs.id())
                .execute();
        ctx
                .insertInto(T_INVENTORY_ITEM, T_INVENTORY_ITEM.ARTIFACT_ID, T_INVENTORY_ITEM.PARTY_ID, T_INVENTORY_ITEM.ACCESS_CONTROL_TYPE_ID)
                .values(classs.id(), classs.ownerId(), "AUTHOR")
                .execute();

    }
}
