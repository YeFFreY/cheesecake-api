package org.yeffrey.cheesecake.features.equipment.create;

import jakarta.inject.Singleton;
import org.yeffrey.cheesecake.features.equipment.create.domain.Equipment;
import org.yeffrey.cheesecake.shared.UserIdHelper;

import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
public class CreateEquipmentService {

    private final CreateEquipmentRepository repository;
    private final UserIdHelper userIdHelper;

    public CreateEquipmentService(CreateEquipmentRepository repository, UserIdHelper userIdHelper) {
        this.repository = repository;
        this.userIdHelper = userIdHelper;
    }


    @Transactional
    public UUID create(CreateEquipmentCommand command) {

        Equipment equipment = new Equipment(
                UUID.randomUUID(),
                userIdHelper.getCurrentOrThrow(),
                command.name(),
                command.description()
        );
        this.repository.create(equipment);
        return equipment.id();
    }
}
