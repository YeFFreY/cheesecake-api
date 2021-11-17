package org.yeffrey.cheesecake.features.activityvariant.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.activityvariant.create.domain.ActivityVariant;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateActivityVariantService {
    private final CreateActivityVariantRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateActivityVariantService(CreateActivityVariantRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateActivityVariantCommand command) {
        if (activityExists(command.activityId(), userIdHelper.getCurrentOrThrow())) {
            ActivityVariant variant = new ActivityVariant(UUID.randomUUID(), command.activityId(), command.name(), command.description());
            return repository.create(variant) ? Optional.of(variant.id()) : Optional.empty();
        }

        return Optional.empty();
    }

    private boolean activityExists(UUID activityId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId);
    }

}
