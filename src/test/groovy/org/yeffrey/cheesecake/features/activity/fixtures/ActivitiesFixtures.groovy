package org.yeffrey.cheesecake.features.activity.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.activity.create.CreateActivityCommand
import org.yeffrey.cheesecake.features.activity.details.domain.ActivityDetails
import org.yeffrey.cheesecake.features.activity.edit.EditActivityCommand
import org.yeffrey.cheesecake.features.activity.list.domain.ActivityOverview
import spock.lang.Shared

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activities")
interface ActivityClient {
    @Get
    HttpResponse<QueryResult<List<ActivityOverview>>> list()

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityCommand command)

    @Get("/{activityId}")
    HttpResponse<QueryResult<ActivityDetails>> details(@PathVariable UUID activityId)

    @Get("/edit/{activityId}")
    HttpResponse<QueryResult<EditActivityCommand>> edit(@PathVariable UUID activityId)

    @Post("/edit/{activityId}")
    HttpResponse<CommandResult<UUID>> update(@PathVariable UUID activityId, @Body EditActivityCommand command)

}

trait ActivitiesFixtures {
    abstract Faker getFaker()

    @Shared
    @Inject
    ActivityClient activityClient

    UUID createActivity(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return activityClient.create(new CreateActivityCommand(name, description)).body().data()
    }

    ActivityDetails activityDetails(UUID activityId) {
        return activityClient.details(activityId).body().data()
    }
}