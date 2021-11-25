package org.yeffrey.cheesecake.features.event.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Singleton
public class ListEventService {

    private final ListEventRepository repository;
    private final UserIdHelper userIdHelper;

    public ListEventService(ListEventRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<CalendarEvent> list(UUID calendarId, OffsetDateTime date) {
        OffsetDateTime monday = date.with(previousOrSame(MONDAY));
        OffsetDateTime sunday = date.with(nextOrSame(SUNDAY));
        return this.repository.list(calendarId, monday, sunday, userIdHelper.getCurrentOrThrow());
    }
}
