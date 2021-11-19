package org.yeffrey.cheesecake.features.calendar.list;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;

@Validated
@Controller("/api/calendars")
public class ListCalendarController {

    private final ListCalendarService service;

    public ListCalendarController(ListCalendarService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public QueryResult<List<CalendarOverview>> list() {
        return new QueryResult<>(service.list());
    }
}
