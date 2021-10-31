package org.yeffrey.cheesecake.features.activity.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.activity.create.domain.Activity;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
public class CreateActivityService {

    private final CreateActivityRepository repository;
    private final UserIdHelper userIdHelper;

    public CreateActivityService(CreateActivityRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public UUID create(CreateActivityCommand command) {

        Activity activity = new Activity(
                UUID.randomUUID(),
                userIdHelper.getCurrentOrThrow(),
                command.name(),
                command.description()
        );
        this.repository.create(activity);
        return activity.id();
    }

}
