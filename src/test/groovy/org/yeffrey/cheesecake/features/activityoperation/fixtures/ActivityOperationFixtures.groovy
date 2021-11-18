package org.yeffrey.cheesecake.features.activityoperation.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.activityoperation.create.CreateActivityOperationCommand
import org.yeffrey.cheesecake.features.activityoperation.create.OperationType
import org.yeffrey.cheesecake.features.activityoperation.delete.DeleteActivityOperationCommand
import org.yeffrey.cheesecake.features.activityoperation.list.ActivityOperation

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activity-operations")
interface ActivityOperationClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityOperationCommand command)

    @Get("/available/{activityId}")
    HttpResponse<QueryResult<List<OperationType>>> listAvailableOperationTypes(UUID activityId)


    @Get('/{activityId}')
    HttpResponse<QueryResult<List<ActivityOperation>>> list(UUID activityId)


    @Post('/delete')
    HttpResponse<CommandResult<Void>> delete(@Body DeleteActivityOperationCommand command)
}

trait ActivityOperationFixtures {
    abstract Faker getFaker()

    @Inject()
    ActivityOperationClient activityOperationClient

    HttpStatus createActivityOperation(UUID activityId, String type, String description = faker.lorem().sentence()) {
        return activityOperationClient.create(new CreateActivityOperationCommand(activityId, type, description)).status()
    }

    List<ActivityOperation> listActivityOperations(UUID activityId) {
        return activityOperationClient.list(activityId).body().data()
    }
}