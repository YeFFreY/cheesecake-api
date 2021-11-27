package org.yeffrey.cheesecake.features.courseactivity.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Singleton
public class ListCourseActivityService {

    private final ListCourseActivityRepository repository;
    private final UserIdHelper userIdHelper;

    public ListCourseActivityService(ListCourseActivityRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<CourseActivity> list(UUID courseId) {
        if (courseExists(courseId, userIdHelper.getCurrentOrThrow())) {
            return this.repository.list(courseId, userIdHelper.getCurrentOrThrow());
        }
        // todo should I return an optional to get a 404 in the front end ? hmmm
        return Collections.emptyList();
    }

    private boolean courseExists(UUID courseId, UUID userId) {
        return repository.courseExists(courseId, userId);
    }
}
