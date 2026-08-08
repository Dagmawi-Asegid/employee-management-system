package com.dagmawiasegid.employeemanagement.exception;

/**
 * Thrown when attempting to create or update an employee with an email
 * address that already belongs to another employee record.
 * Translated to an HTTP 409 response by {@link GlobalExceptionHandler}.
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
