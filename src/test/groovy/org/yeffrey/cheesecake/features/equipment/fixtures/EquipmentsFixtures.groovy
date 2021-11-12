package org.yeffrey.cheesecake.features.equipment.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.features.equipment.create.CreateEquipmentCommand
import spock.lang.Shared

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/equipments")
interface EquipmentClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateEquipmentCommand command)

}

trait EquipmentsFixtures {
    abstract Faker getFaker()

    @Shared
    @Inject
    EquipmentClient equipmentClient

    UUID createEquipment(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return equipmentClient.create(new CreateEquipmentCommand(name, description)).body().data()
    }

}
