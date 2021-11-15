package org.yeffrey.cheesecake.features.activityoperation.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateActivityOperationService {
    private final CreateActivityOperationRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateActivityOperationService(CreateActivityOperationRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateActivityOperationCommand command) {
        if (activityAndOperationTypeExists(command.activityId(), command.type(), userIdHelper.getCurrentOrThrow())
                && repository.create(command.activityId(), command.type(), command.description())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityAndOperationTypeExists(UUID activityId, String type, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && repository.operationTypeExists(type);
    }

    @Transactional
    public List<OperationType> listAvailableTypes(UUID activityId) {
        return this.repository.listAvailableTypes(activityId);
    }
}
