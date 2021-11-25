package org.yeffrey.cheesecake.features.event.fixtures

import com.github.javafaker.Faker
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import org.yeffrey.cheesecake.core.infra.rest.QueryResult
import org.yeffrey.cheesecake.features.event.list.CalendarEvent

@Header(name = "Basic", value = "bob@bob.com secret77")
@Client("/api/events")
interface EventClient {

    @Get("/{calendarId}")
    HttpResponse<QueryResult<List<CalendarEvent>>> list(@PathVariable UUID calendarId, @QueryValue String date)
}

trait EventFixtures {
    abstract Faker getFaker()

    @Inject
    EventClient eventClient
}