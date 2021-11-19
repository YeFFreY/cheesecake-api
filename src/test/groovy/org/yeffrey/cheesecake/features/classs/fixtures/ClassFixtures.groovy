package org.yeffrey.cheesecake.features.classs.fixtures

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
import org.yeffrey.cheesecake.features.classs.create.CreateClassCommand
import org.yeffrey.cheesecake.features.classs.list.ClassOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/classes")
interface ClassClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateClassCommand command)

    @Get
    HttpResponse<QueryResult<List<ClassOverview>>> list()
}

trait ClassFixtures {
    abstract Faker getFaker()

    @Inject
    ClassClient classClient

    UUID createClass(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return classClient.create(new CreateClassCommand(name, description)).body().data()
    }

}