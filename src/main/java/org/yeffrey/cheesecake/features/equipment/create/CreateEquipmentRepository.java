package org.yeffrey.cheesecake.features.equipment.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.yeffrey.cheesecake.features.equipment.create.domain.Equipment;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateEquipmentRepository {
    private final DSLContext ctx;

    public CreateEquipmentRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(Equipment equipment) {
        ctx
                .insertInto(T_ARTIFACT, T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION, T_ARTIFACT.TYPE_ID)
                .values(equipment.id(), equipment.name(), equipment.description(), "EQUIPMENT")
                .execute();
        ctx
                .insertInto(T_EQUIPMENT, T_EQUIPMENT.ID)
                .values(equipment.id())
                .execute();
        ctx
                .insertInto(T_INVENTORY_ITEM, T_INVENTORY_ITEM.ARTIFACT_ID, T_INVENTORY_ITEM.PARTY_ID, T_INVENTORY_ITEM.ACCESS_CONTROL_TYPE_ID)
                .values(equipment.id(), equipment.ownerId(), "AUTHOR")
                .execute();

    }
}
