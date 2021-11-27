package org.yeffrey.cheesecake.features.courseactivity.list;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.yeffrey.cheesecake.Tables.*;

@Singleton
public class ListCourseActivityRepository {
    private final DSLContext ctx;

    public ListCourseActivityRepository(DSLContext ctx) {
        this.ctx = ctx;
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

    public List<CourseActivity> list(UUID courseId, UUID userId) {
        return ctx.select(T_COURSE_SECTION.ACTIVITY_ID, T_ARTIFACT.NAME, T_COURSE_SECTION_TYPE.ID, T_COURSE_SECTION_TYPE.DESCRIPTION)
                .from(T_COURSE_SECTION)
                .innerJoin(T_COURSE_SECTION_TYPE).on(T_COURSE_SECTION_TYPE.ID.eq(T_COURSE_SECTION.TYPE_ID))
                .innerJoin(T_ACTIVITY).on(T_ACTIVITY.ID.eq(T_COURSE_SECTION.ACTIVITY_ID))
                .innerJoin(T_ARTIFACT).on(T_ARTIFACT.ID.eq(T_ACTIVITY.ID))
                .innerJoin(T_INVENTORY_ITEM).on(T_INVENTORY_ITEM.ARTIFACT_ID.eq(T_ARTIFACT.ID))
                .where(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .and(T_COURSE_SECTION.COURSE_ID.eq(courseId))
                .and(T_INVENTORY_ITEM.PARTY_ID.eq(userId))
                .fetchInto(CourseActivity.class);
    }
}
