package org.yeffrey.cheesecake.features.activity.details;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.activity.details.domain.ActivityDetails;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class DetailsActivityService {
    private final DetailsActivityRepository repository;
    private final UserIdHelper userIdHelper;

    public DetailsActivityService(DetailsActivityRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public Optional<ActivityDetails> details(UUID activityId) {
        return this.repository.details(activityId, userIdHelper.getCurrentOrThrow());
    }
}
