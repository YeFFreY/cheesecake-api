package org.yeffrey.cheesecake.core.infra.rest;

public class QueryResult<T> {
    public final T data;

    private QueryResult(T data) {
        this.data = data;
    }

    public static <T> QueryResult<T> from(T data) {
        return new QueryResult<>(data);
    }
}
