package org.yeffrey.cheesecake.features.activityskill.delete;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DeleteActivitySkillService {
    private final DeleteActivitySkillRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public DeleteActivitySkillService(DeleteActivitySkillRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> delete(DeleteActivitySkillCommand command) {
        if (activityAndSkillExists(command.activityId(), command.skillId(), userIdHelper.getCurrentOrThrow())
                && repository.delete(command.activityId(), command.skillId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean activityAndSkillExists(UUID activityId, UUID skillId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && artifactHelper.artifactExists(skillId, "SKILL", userId);
    }
}
