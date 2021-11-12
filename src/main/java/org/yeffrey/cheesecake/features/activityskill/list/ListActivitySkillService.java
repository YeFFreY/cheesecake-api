package org.yeffrey.cheesecake.features.activityskill.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Singleton
public class ListActivitySkillService {

    private final ListActivitySkillRepository repository;
    private final UserIdHelper userIdHelper;

    public ListActivitySkillService(ListActivitySkillRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ActivitySkill> list(UUID activityId) {
        return this.repository.list(activityId, userIdHelper.getCurrentOrThrow());
    }
}
