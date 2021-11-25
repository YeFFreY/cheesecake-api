package org.yeffrey.cheesecake.features.course.create;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import static org.yeffrey.cheesecake.Tables.T_COURSE;
import static org.yeffrey.cheesecake.Tables.T_EVENT;

@Singleton
public class CreateCourseRepository {

    private final DSLContext ctx;

    public CreateCourseRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void create(CourseDomain course) {
        ctx
                .insertInto(T_EVENT, T_EVENT.ID, T_EVENT.TYPE_ID, T_EVENT.CALENDAR_ID, T_EVENT.START_DATE, T_EVENT.END_DATE)
                .values(course.id(), "COURSE", course.calendarId(), course.start(), course.end())
                .execute();
        ctx
                .insertInto(T_COURSE, T_COURSE.ID, T_COURSE.CLASS_ID)
                .values(course.id(), course.classId())
                .execute();


    }
}
