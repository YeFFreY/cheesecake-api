package org.yeffrey.cheesecake.features.event.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListEventRepository {
    private final DSLContext ctx;

    public ListEventRepository(DSLContext ctx) {
        this.ctx = ctx;
    }


    public List<CalendarEvent> list(UUID calendarId, OffsetDateTime start, OffsetDateTime end, UUID userId) {
        var classes = ctx.select(T_COURSE.ID.as("courseId"), T_ARTIFACT.NAME.as("className"))
                .from(T_CLASS)
                .innerJoin(T_COURSE).on(T_COURSE.CLASS_ID.eq(T_CLASS.ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_CLASS.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CLASS"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .asTable();
        return ctx.select(T_EVENT.START_DATE, T_EVENT.END_DATE, classes.field("className", T_ARTIFACT.NAME.getDataType()), T_EVENT.TYPE_ID)
                .from(T_EVENT)
                .leftJoin(classes).on(classes.field("courseId", T_COURSE.ID.getDataType()).eq(T_EVENT.ID))
                .innerJoin(T_CALENDAR).on(T_CALENDAR.ID.eq(T_EVENT.CALENDAR_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_CALENDAR.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CALENDAR"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .and(T_CALENDAR.ID.eq(calendarId))
                .and(T_EVENT.START_DATE.ge(start)).and(T_EVENT.END_DATE.le(end))
                .fetchInto(CalendarEvent.class);

        // todo event ending in the current week should be retrieved, and event start in current week should also be retrieved
    }
}
