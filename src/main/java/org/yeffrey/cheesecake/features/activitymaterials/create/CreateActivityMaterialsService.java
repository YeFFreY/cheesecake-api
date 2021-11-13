package org.yeffrey.cheesecake.features.activitymaterials.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateActivityMaterialsService {
    private final CreateActivityMaterialsRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateActivityMaterialsService(CreateActivityMaterialsRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateActivityMaterialsCommand command) {
        if (activityAndEquipmentExists(command.activityId(), command.equipmentId(), userIdHelper.getCurrentOrThrow())
                && repository.create(command.activityId(), command.equipmentId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityAndEquipmentExists(UUID activityId, UUID equipmentId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && artifactHelper.artifactExists(equipmentId, "EQUIPMENT", userId);
    }

    @Transactional
    public List<EquipmentOverview> listAvailable(UUID activityId) {
        return this.repository.listAvailable(activityId, userIdHelper.getCurrentOrThrow());
    }
}
