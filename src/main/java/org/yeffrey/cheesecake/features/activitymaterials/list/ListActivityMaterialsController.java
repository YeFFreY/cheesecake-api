package org.yeffrey.cheesecake.features.activitymaterials.list;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;
import java.util.UUID;

@Validated
@Controller("/api/activity-materials")
public class ListActivityMaterialsController {
    private final ListActivityMaterialsService service;

    public ListActivityMaterialsController(ListActivityMaterialsService service) {
        this.service = service;
    }

    @Get("/{activityId}")
    public QueryResult<List<ActivityMaterials>> list(@PathVariable UUID activityId) {
        return new QueryResult<>(service.list(activityId));
    }
}
