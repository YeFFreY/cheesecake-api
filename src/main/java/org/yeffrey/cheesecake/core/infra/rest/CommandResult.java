package org.yeffrey.cheesecake.core.infra.rest;

public class CommandResult<T> {
    private T data;

    protected CommandResult() {
        // because of spock testing
    }

    private CommandResult(T data) {
        this.data = data;
    }

    public static <T> CommandResult<T> from(T data) {
        return new CommandResult<>(data);
    }

    public T getData() {
        return data;
    }
}
