package org.yeffrey.cheesecake.features.courseactivity.delete;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class DeleteCourseActivityRepository {
    private final DSLContext ctx;

    public DeleteCourseActivityRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public boolean delete(UUID courseId, UUID activityId, String typeId) {
        return ctx.delete(T_COURSE_SECTION)
                .where(T_COURSE_SECTION.ACTIVITY_ID.eq(activityId))
                .and(T_COURSE_SECTION.COURSE_ID.eq(courseId))
                .and(T_COURSE_SECTION.TYPE_ID.eq(typeId))
                .execute() == 1;
    }

    public boolean sectionTypeExists(String typeId) {
        Integer count = ctx.selectCount().from(T_COURSE_SECTION_TYPE).where(T_COURSE_SECTION_TYPE.ID.eq(typeId)).fetchOne(0, int.class);
        return Optional.ofNullable(count).map(c -> c == 1).orElse(false);
    }

    public boolean courseExists(UUID courseId, UUID userId) {
        Integer count = ctx.selectCount()
                .from(T_EVENT)
                .innerJoin(T_COURSE).on(T_COURSE.ID.eq(T_EVENT.ID))
                .innerJoin(T_CALENDAR).on(T_CALENDAR.ID.eq(T_EVENT.CALENDAR_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_CALENDAR.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("CALENDAR"))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .and(T_COURSE.ID.eq(courseId))
                .fetchOne(0, int.class);

        return Optional.ofNullable(count).map(c -> c == 1).orElse(false);
    }
}
