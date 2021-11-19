package org.yeffrey.cheesecake.features.classs.list;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import org.yeffrey.cheesecake.core.infra.rest.QueryResult;

import java.util.List;

@Validated
@Controller("/api/classes")
public class ListClassController {

    private final ListClassService service;

    public ListClassController(ListClassService service) {
        this.service = service;
    }

    @Get
    @Status(HttpStatus.OK)
    public QueryResult<List<ClassOverview>> list() {
        return new QueryResult<>(service.list());
    }
}
