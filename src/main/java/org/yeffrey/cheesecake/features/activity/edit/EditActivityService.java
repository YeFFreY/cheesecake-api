package org.yeffrey.cheesecake.features.activity.edit;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class EditActivityService {

    private final EditActivityRepository repository;
    private final UserIdHelper userIdHelper;

    public EditActivityService(EditActivityRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<EditActivityCommand> edit(UUID activityId) {
        return this.repository.edit(activityId, userIdHelper.getCurrentOrThrow());
    }

    @Transactional
    public Optional<UUID> update(UUID activityId, EditActivityCommand command) {
        return Optional.of(this.repository.update(activityId, userIdHelper.getCurrentOrThrow(), command.name(), command.description()))
                .filter(rowUpdated -> rowUpdated)
                .map(rowUpdated -> activityId);
    }
}
