package org.yeffrey.cheesecake.features.activitymaterials.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Singleton
public class ListActivityMaterialsService {

    private final ListActivityMaterialsRepository repository;
    private final UserIdHelper userIdHelper;

    public ListActivityMaterialsService(ListActivityMaterialsRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ActivityMaterials> list(UUID activityId) {
        return this.repository.list(activityId, userIdHelper.getCurrentOrThrow());
    }
}
