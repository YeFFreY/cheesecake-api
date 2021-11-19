package org.yeffrey.cheesecake.features.calendar.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.T_ARTIFACT;
import static org.yeffrey.cheesecake.Tables.T_INVENTORY_ITEM;

@Singleton
public class ListCalendarRepository {
    private final DSLContext ctx;

    public ListCalendarRepository(DSLContext ctx) {
        this.ctx = ctx;
    }


    public List<CalendarOverview> list(UUID userId) {
        return ctx.select(T_ARTIFACT.ID, T_ARTIFACT.NAME, T_ARTIFACT.DESCRIPTION)
                .from(T_ARTIFACT)
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CALENDAR"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(CalendarOverview.class);
    }
}
