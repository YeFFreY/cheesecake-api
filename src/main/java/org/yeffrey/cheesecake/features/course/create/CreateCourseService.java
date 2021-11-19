package org.yeffrey.cheesecake.features.course.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.ArtifactHelper;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreateCourseService {

    private final CreateCourseRepository repository;
    private final ArtifactHelper artifactHelper;
    private final UserIdHelper userIdHelper;

    public CreateCourseService(CreateCourseRepository repository, ArtifactHelper artifactHelper, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.artifactHelper = artifactHelper;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<UUID> create(CreateCourseCommand command) {
        if (calendarAndClassExist(command.calendarId(), command.classId(), userIdHelper.getCurrentOrThrow())) {
            CourseDomain course = new CourseDomain(
                    UUID.randomUUID(),
                    command.calendarId(),
                    command.classId(),
                    command.start(),
                    command.end()
            );
            this.repository.create(course);
            return Optional.of(course.id());
        }
        return Optional.empty();
    }

    private boolean calendarAndClassExist(UUID calendarId, UUID classId, UUID userId) {
        return artifactHelper.artifactExists(calendarId, "CALENDAR", userId)
                && artifactHelper.artifactExists(classId, "CLASS", userId);
    }

}
