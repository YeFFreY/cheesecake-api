package org.yeffrey.cheesecake.features.activity.list;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;
import org.yeffrey.cheesecake.features.activity.list.domain.ActivityOverview;

import java.util.List;

@Validated
@Controller("/api/activities")
public class ListActivityController {

    private final ListActivityService service;

    public ListActivityController(ListActivityService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public QueryResult<List<ActivityOverview>> list() {
        return new QueryResult<>(service.list());
    }
}
