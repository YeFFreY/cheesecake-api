package org.yeffrey.cheesecake.features.classs.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
public class CreateClassService {

    private final CreateClassRepository repository;
    private final UserIdHelper userIdHelper;

    public CreateClassService(CreateClassRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }

    @Transactional
    public UUID create(CreateClassCommand command) {

        ClassDomain classs = new ClassDomain(
                UUID.randomUUID(),
                userIdHelper.getCurrentOrThrow(),
                command.name(),
                command.description()
        );
        this.repository.create(classs);
        return classs.id();
    }

}
