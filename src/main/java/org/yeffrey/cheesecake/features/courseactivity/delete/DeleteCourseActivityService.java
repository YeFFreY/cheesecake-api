package org.yeffrey.cheesecake.features.courseactivity.delete;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DeleteCourseActivityService {
    private final DeleteCourseActivityRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public DeleteCourseActivityService(DeleteCourseActivityRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> delete(DeleteCourseActivityCommand command) {
        if (courseAndActivityAndTypeExists(command.courseId(), command.activityId(), command.courseSectionTypeId(), userIdHelper.getCurrentOrThrow())
                && repository.delete(command.courseId(), command.activityId(), command.courseSectionTypeId())) {
            return Optional.of(command.courseId());
        }
        return Optional.empty();
    }

    private boolean courseAndActivityAndTypeExists(UUID courseId, UUID activityId, String typeId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && repository.courseExists(courseId, userId)
                && repository.sectionTypeExists(typeId);
    }
}
