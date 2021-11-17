package org.yeffrey.cheesecake.features.activityvariant.list;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;
import java.util.UUID;

@Validated
@Controller("/api/activity-variants")
public class ListActivityVariantController {
    private final ListActivityVariantService service;

    public ListActivityVariantController(ListActivityVariantService service) {
        this.service = service;
    }

    @Get("/{activityId}")
    public QueryResult<List<ActivityVariant>> list(@PathVariable UUID activityId) {
        return new QueryResult<>(service.list(activityId));
    }
}
