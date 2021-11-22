package org.yeffrey.cheesecake.features.course.details;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class DetailsCourseRepository {
    private final DSLContext ctx;

    public DetailsCourseRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public Optional<CourseDetails> details(UUID courseId, UUID userId) {
        Optional<ClassOverview> classOverview = ctx.select(T_CLASS.ID, T_ARTIFACT.NAME)
                .from(T_CLASS)
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_CLASS.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .innerJoin(T_COURSE).on(T_COURSE.CLASS_ID.eq(T_CLASS.ID).and(T_COURSE.ID.eq(courseId)))
                .where(T_ARTIFACT.TYPE_ID.eq("CLASS"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchOptionalInto(ClassOverview.class);

        return ctx.select(T_EVENT.ID, T_EVENT.START_DATE, T_EVENT.END_DATE)
                .from(T_EVENT)
                .innerJoin(T_COURSE).on(T_COURSE.ID.eq(T_EVENT.ID))
                .innerJoin(T_CALENDAR).on(T_CALENDAR.ID.eq(T_EVENT.CALENDAR_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_CALENDAR.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CALENDAR"))
                .and(T_COURSE.ID.eq(courseId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchOptional(data -> new CourseDetails(data.value1(), classOverview.orElse(null /* todo maybe I should throw if class not accessible anymore or create a dummy placeholder ? */), data.value2(), data.value3()));
    }


}
