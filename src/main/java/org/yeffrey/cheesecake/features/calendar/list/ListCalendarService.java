package org.yeffrey.cheesecake.features.calendar.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ListCalendarService {

    private final ListCalendarRepository repository;
    private final UserIdHelper userIdHelper;

    public ListCalendarService(ListCalendarRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<CalendarOverview> list() {
        return this.repository.list(userIdHelper.getCurrentOrThrow());
    }
}
