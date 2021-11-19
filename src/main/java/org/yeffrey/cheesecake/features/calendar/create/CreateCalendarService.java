package org.yeffrey.cheesecake.features.calendar.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
public class CreateCalendarService {

    private final CreateCalendarRepository repository;
    private final UserIdHelper userIdHelper;

    public CreateCalendarService(CreateCalendarRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public UUID create(CreateCalendarCommand command) {

        CalendarDomain calendar = new CalendarDomain(
                UUID.randomUUID(),
                userIdHelper.getCurrentOrThrow(),
                command.name(),
                command.description()
        );
        this.repository.create(calendar);
        return calendar.id();
    }

}
