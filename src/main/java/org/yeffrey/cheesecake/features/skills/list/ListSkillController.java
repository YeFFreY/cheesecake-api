package org.yeffrey.cheesecake.features.skills.list;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;
import org.yeffrey.cheesecake.features.skills.list.domain.SkillOverview;

import java.util.List;

@Validated
@Controller("/api/skills")
public class ListSkillController {

    private final ListSkillService service;

    public ListSkillController(ListSkillService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public QueryResult<List<SkillOverview>> list() {
        return new QueryResult<>(service.list());
    }
}
