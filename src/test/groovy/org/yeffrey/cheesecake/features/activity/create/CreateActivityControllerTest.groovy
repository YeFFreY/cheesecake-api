package org.yeffrey.cheesecake.features.activity.create

import com.github.javafaker.Faker
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.jooq.DSLContext
import org.yeffrey.cheesecake.core.infra.rest.CommandResult
import spock.lang.Specification
import static org.yeffrey.cheesecake.Tables.T_ARTIFACT

@Client("/api/activities")
interface CreateActivityClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateActivityCommand command)
}

@MicronautTest
@Property(name = "security.user.bob", value = "true")
class CreateActivityControllerTest extends Specification {

    @Inject
    CreateActivityClient client

    @Inject
    DSLContext ctx

    Faker faker = new Faker()

    void "user creates a new activity"() {
        given: "valid details of a new activity"
        def command = new CreateActivityCommand(faker.lorem().sentence(3), faker.lorem().sentence())

        when: "creates activity"
        var response = client.create(command)

        then: "new activity id is returned"
        response.status == HttpStatus.CREATED
        def activityId = response.body().data
        activityId != null

        then: "activity id is found in database"
        def count = ctx.selectCount().from(T_ARTIFACT)
                .where(T_ARTIFACT.ID.eq(activityId)).and(T_ARTIFACT.TYPE_ID.eq("ACTIVITY"))
                .fetchOne(0, long.class)
        count == 1
    }

    void "new activity requires name and description"() {
        given: "no name and no description"
        def invalidCommand = new CreateActivityCommand("", "")

        when: "creates activity"
        client.create(invalidCommand)

        then: "activity is not created and error is returned"
        HttpClientResponseException error = thrown()
        error.status == HttpStatus.BAD_REQUEST
    }

}
