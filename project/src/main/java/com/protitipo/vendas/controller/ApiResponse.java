package com.protitipo.vendas.controller;

public record ApiResponse<T>(int status, T data, String error) {
    public static <T> ApiResponse<T> ok(final T data) {
        return new ApiResponse<>(200, data, null);
    }

    public static <T> ApiResponse<T> created(final T data) {
        return new ApiResponse<>(201, data, null);
    }

    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(204, null, null);
    }

    public static <T> ApiResponse<T> badRequest(final String error) {
        return new ApiResponse<>(400, null, error);
    }

    public static <T> ApiResponse<T> notFound(final String error) {
        return new ApiResponse<>(404, null, error);
    }
}
