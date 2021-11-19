package org.yeffrey.cheesecake.features.calendar.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class CreateCalendarRepository {

    private final DSLContext ctx;

    public CreateCalendarRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(CalendarDomain calendar) {
        ctx
                .insertInto(T_ARTIFACT, T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION, T_ARTIFACT.TYPE_ID)
                .values(calendar.id(), calendar.name(), calendar.description(), "CALENDAR")
                .execute();
        ctx
                .insertInto(T_CALENDAR, T_CALENDAR.ID)
                .values(calendar.id())
                .execute();
        ctx
                .insertInto(T_INVENTORY_ITEM, T_INVENTORY_ITEM.ARTIFACT_ID, T_INVENTORY_ITEM.PARTY_ID, T_INVENTORY_ITEM.ACCESS_CONTROL_TYPE_ID)
                .values(calendar.id(), calendar.ownerId(), "AUTHOR")
                .execute();

    }
}
