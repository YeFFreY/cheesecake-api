package org.yeffrey.cheesecake.features.activityvariant.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Singleton
public class ListActivityVariantService {

    private final ListActivityVariantRepository repository;
    private final UserIdHelper userIdHelper;

    public ListActivityVariantService(ListActivityVariantRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ActivityVariant> list(UUID activityId) {
        return this.repository.list(activityId, userIdHelper.getCurrentOrThrow());
    }
}
