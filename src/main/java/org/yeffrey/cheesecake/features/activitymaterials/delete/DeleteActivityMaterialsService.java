package org.yeffrey.cheesecake.features.activitymaterials.delete;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DeleteActivityMaterialsService {
    private final DeleteActivityMaterialsRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public DeleteActivityMaterialsService(DeleteActivityMaterialsRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> delete(DeleteActivityMaterialsCommand command) {
        if (activityAndEquipmentExists(command.activityId(), command.equipmentId(), userIdHelper.getCurrentOrThrow())
                && repository.delete(command.activityId(), command.equipmentId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityAndEquipmentExists(UUID activityId, UUID equipmentId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && artifactHelper.artifactExists(equipmentId, "EQUIPMENT", userId);
    }
}
