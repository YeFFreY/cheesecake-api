package org.yeffrey.cheesecake.features.course.details;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DetailsCourseService {
    private final DetailsCourseRepository repository;
    private final UserIdHelper userIdHelper;

    public DetailsCourseService(DetailsCourseRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<CourseDetails> details(UUID courseId) {
        return this.repository.details(courseId, userIdHelper.getCurrentOrThrow());
    }
}
