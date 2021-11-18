package org.yeffrey.cheesecake.features.activityoperation.delete;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DeleteActivityOperationService {
    private final DeleteActivityOperationRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public DeleteActivityOperationService(DeleteActivityOperationRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> delete(DeleteActivityOperationCommand command) {
        if (activityExists(command.activityId(), userIdHelper.getCurrentOrThrow())
                && repository.delete(command.activityId(), command.operationTypeId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityExists(UUID activityId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId);
    }
}
