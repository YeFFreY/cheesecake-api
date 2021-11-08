package org.yeffrey.cheesecake.features.skill.fixtures

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
import org.yeffrey.cheesecake.features.skills.create.CreateSkillCommand
import org.yeffrey.cheesecake.features.skills.list.domain.SkillOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/skills")
interface SkillClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateSkillCommand command)

    @Get
    HttpResponse<QueryResult<List<SkillOverview>>> list()
}

trait SkillsFixtures {
    abstract Faker getFaker()

    @Inject
    SkillClient skillClient

    UUID createSkill(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return skillClient.create(new CreateSkillCommand(name, description)).body().data()
    }

}