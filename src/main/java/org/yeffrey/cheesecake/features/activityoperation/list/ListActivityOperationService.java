package org.yeffrey.cheesecake.features.activityoperation.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Singleton
public class ListActivityOperationService {

    private final ListActivityOperationRepository repository;
    private final UserIdHelper userIdHelper;

    public ListActivityOperationService(ListActivityOperationRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ActivityOperation> list(UUID activityId) {
        return this.repository.list(activityId, userIdHelper.getCurrentOrThrow());
    }
}
