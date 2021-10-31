package org.yeffrey.cheesecake.features.activity.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.activity.list.domain.ActivityOverview;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ListActivityService {

    private final ListActivityRepository repository;
    private final UserIdHelper userIdHelper;

    public ListActivityService(ListActivityRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ActivityOverview> list() {
        return this.repository.list(userIdHelper.getCurrentOrThrow());
    }
}
