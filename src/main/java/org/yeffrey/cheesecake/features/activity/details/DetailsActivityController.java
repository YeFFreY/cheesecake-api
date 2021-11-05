package org.yeffrey.cheesecake.features.activity.details;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;
import org.yeffrey.cheesecake.features.activity.details.domain.ActivityDetails;

import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/api/activities/{activityId}")
public class DetailsActivityController {

    private final DetailsActivityService service;

    public DetailsActivityController(DetailsActivityService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public Optional<QueryResult<ActivityDetails>> details(@PathVariable UUID activityId) {
        return service.details(activityId).map(QueryResult::new);
    }

}
