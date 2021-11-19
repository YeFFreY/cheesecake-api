package org.yeffrey.cheesecake.features.calendar.fixtures

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
import org.yeffrey.cheesecake.features.calendar.create.CreateCalendarCommand
import org.yeffrey.cheesecake.features.calendar.list.CalendarOverview

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/calendars")
interface CalendarClient {
    @Post
    HttpResponse<CommandResult<UUID>> create(@Body CreateCalendarCommand command)

    @Get
    HttpResponse<QueryResult<List<CalendarOverview>>> list()
}

trait CalendarFixtures {
    abstract Faker getFaker()

    @Inject
    CalendarClient calendarClient

    UUID createCalendar(name = faker.lorem().sentence(), description = faker.lorem().sentence()) {
        return calendarClient.create(new CreateCalendarCommand(name, description)).body().data()
    }

}