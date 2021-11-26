package org.yeffrey.cheesecake.features.courseactivity.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateCourseActivityService {
    private final CreateCourseActivityRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateCourseActivityService(CreateCourseActivityRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateCourseActivityCommand command) {
        if (courseAndActivityAndTypeExists(command.courseId(), command.activityId(), command.courseSectionTypeId(), userIdHelper.getCurrentOrThrow())
                && repository.create(command.courseId(), command.activityId(), command.courseSectionTypeId())) {
            return Optional.of(command.activityId());
        }
        return Optional.empty();
    }

    private boolean courseAndActivityAndTypeExists(UUID courseId, UUID activityId, String typeId, UUID userId) {
        return artifactHelper.artifactExists(activityId, "ACTIVITY", userId)
                && repository.courseExists(courseId, userId)
                && repository.sectionTypeExists(typeId);
    }

    @Transactional
    public List<SectionType> listAvailableTypes() {
        return this.repository.listAvailableTypes();
    }
}
