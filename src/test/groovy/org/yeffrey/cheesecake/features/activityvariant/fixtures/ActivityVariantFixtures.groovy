package org.yeffrey.cheesecake.features.activityvariant.fixtures

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
import org.yeffrey.cheesecake.features.activityvariant.create.CreateActivityVariantCommand
import org.yeffrey.cheesecake.features.activityvariant.delete.DeleteActivityVariantCommand
import org.yeffrey.cheesecake.features.activityvariant.list.ActivityVariant

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activity-variants")
interface ActivityVariantClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityVariantCommand command)


    @Get('/{activityId}')
    HttpResponse<QueryResult<List<ActivityVariant>>> list(UUID activityId)

    @Post('/delete')
    HttpResponse<CommandResult<Void>> delete(@Body DeleteActivityVariantCommand command)
}

trait ActivityVariantFixtures {
    abstract Faker getFaker()

    @Inject
    ActivityVariantClient activityVariantClient

    UUID createActivityVariant(UUID activityId, String name = faker.lorem().sentence(), String description = faker.lorem().sentence()) {
        return activityVariantClient.create(new CreateActivityVariantCommand(activityId, name, description)).body().data()
    }

    List<ActivityVariant> listActivityVariants(UUID activityId) {
        return activityVariantClient.list(activityId).body().data()
    }
}