package org.yeffrey.cheesecake.features.activity.fixtures

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
import org.yeffrey.cheesecake.features.activity.create.CreateActivityCommand
import org.yeffrey.cheesecake.features.activity.list.domain.ActivityOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activities")
interface ActivityClient {
    @Get
    HttpResponse<QueryResult<List<ActivityOverview>>> list()

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityCommand command)
}

trait ActivitiesFixtures {
    abstract Faker getFaker()

    @Inject
    ActivityClient activityClient

    UUID createActivity(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return activityClient.create(new CreateActivityCommand(name, description)).body().data()
    }

}