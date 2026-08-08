package com.dagmawiasegid.employeemanagement.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Simple, consistent error payload returned by the API for any failure
 * response (validation errors, not-found, duplicate email, etc).
 */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {
    public ApiError(int status, String error, String message) {
        this(Instant.now(), status, error, message, null);
    }

    public ApiError(int status, String error, String message, Map<String, String> fieldErrors) {
        this(Instant.now(), status, error, message, fieldErrors);
    }
}
