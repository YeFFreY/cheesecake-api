package org.yeffrey.cheesecake.features.activityoperation.list;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;
import java.util.UUID;

@Validated
@Controller("/api/activity-operations")
public class ListActivityOperationController {
    private final ListActivityOperationService service;

    public ListActivityOperationController(ListActivityOperationService service) {
        this.service = service;
    }

    @Get("/{activityId}")
    public QueryResult<List<ActivityOperation>> list(@PathVariable UUID activityId) {
        return new QueryResult<>(service.list(activityId));
    }
}
