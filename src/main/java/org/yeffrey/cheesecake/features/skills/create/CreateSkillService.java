package org.yeffrey.cheesecake.features.skills.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.skills.create.domain.Skill;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
public class CreateSkillService {

    private final CreateSkillRepository repository;
    private final UserIdHelper userIdHelper;

    public CreateSkillService(CreateSkillRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }


    @Transactional
    public UUID create(CreateSkillCommand command) {

        Skill skill = new Skill(
                UUID.randomUUID(),
                userIdHelper.getCurrentOrThrow(),
                command.name(),
                command.description()
        );
        this.repository.create(skill);
        return skill.id();
    }
}
