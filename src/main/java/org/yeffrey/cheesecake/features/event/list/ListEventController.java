package org.yeffrey.cheesecake.features.event.list;

import io.micronaut.core.convert.format.Format;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Validated
@Controller("/api/events")
public class ListEventController {

    private final ListEventService service;

    public ListEventController(ListEventService service) {
        this.service = service;
    }

    @Get("/{calendarId}")
    @Status(HttpStatus.OK)
    public QueryResult<List<CalendarEvent>> list(@PathVariable UUID calendarId, @QueryValue("date") @Format("yyyy-MM-dd'T'HH:mm:ss.SSSZ") OffsetDateTime date) {
        return new QueryResult<>(service.list(calendarId, date));
    }
}
