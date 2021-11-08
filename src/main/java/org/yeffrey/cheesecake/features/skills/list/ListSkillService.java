package org.yeffrey.cheesecake.features.skills.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.skills.list.domain.SkillOverview;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ListSkillService {

    private final ListSkillRepository repository;
    private final UserIdHelper userIdHelper;

    public ListSkillService(ListSkillRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<SkillOverview> list() {
        return this.repository.list(userIdHelper.getCurrentOrThrow());
    }
}
