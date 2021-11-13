package org.yeffrey.cheesecake.features.activitymaterials.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.activitymaterials.create.CreateActivityMaterialsCommand
import org.yeffrey.cheesecake.features.activitymaterials.create.EquipmentOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activity-materials")
interface ActivityMaterialsClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityMaterialsCommand command)

    @Get("/available/{activityId}")
    HttpResponse<QueryResult<List<EquipmentOverview>>> listAvailable(UUID activityId)

}

trait ActivityMaterialsFixtures {
    abstract Faker getFaker()

    @Inject
    ActivityMaterialsClient activityMaterialsClient
}