package org.yeffrey.cheesecake.features.classs.list;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ListClassService {

    private final ListClassRepository repository;
    private final UserIdHelper userIdHelper;

    public ListClassService(ListClassRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public List<ClassOverview> list() {
        return this.repository.list(userIdHelper.getCurrentOrThrow());
    }
}
