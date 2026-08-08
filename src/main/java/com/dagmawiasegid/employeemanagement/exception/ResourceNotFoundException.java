package com.dagmawiasegid.employeemanagement.exception;

/**
 * Thrown when a requested employee record cannot be found.
 * Translated to an HTTP 404 response by {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
