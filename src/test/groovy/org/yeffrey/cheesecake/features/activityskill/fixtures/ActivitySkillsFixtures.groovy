package org.yeffrey.cheesecake.features.activityskill.fixtures

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
import org.yeffrey.cheesecake.features.activityskill.create.CreateActivitySkillCommand
import org.yeffrey.cheesecake.features.activityskill.create.SkillOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/activity-skills")
interface ActivitySkillClient {

    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivitySkillCommand command)

    @Get("/available/{activityId}")
    HttpResponse<QueryResult<List<SkillOverview>>> listAvailable(UUID activityId)
}

trait ActivitySkillsFixtures {
    abstract Faker getFaker()

    @Inject
    ActivitySkillClient activitySkillClient

    HttpStatus createActivitySkill(UUID activityId, UUID skillId) {
        return activitySkillClient.create(new CreateActivitySkillCommand(activityId, skillId)).status()
    }

}