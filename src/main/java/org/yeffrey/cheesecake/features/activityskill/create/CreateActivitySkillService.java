package org.yeffrey.cheesecake.features.activityskill.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateActivitySkillService {
    private final CreateActivitySkillRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateActivitySkillService(CreateActivitySkillRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateActivitySkillCommand command) {
        if (activityAndSkillExists(command.activityId(), command.skillId(), userIdHelper.getCurrentOrThrow())
                && repository.create(command.activityId(), command.skillId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityAndSkillExists(UUID activityId, UUID skillId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && artifactHelper.artifactExists(skillId, "SKILL", userId);
    }

    @Transactional
    public List<SkillOverview> listAvailable(UUID activityId) {
        return this.repository.listAvailable(activityId, userIdHelper.getCurrentOrThrow());
    }
}
