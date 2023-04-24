package pl.university.applicationserver.authServerIntegration.exception;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(HttpStatus status, String message) { }
