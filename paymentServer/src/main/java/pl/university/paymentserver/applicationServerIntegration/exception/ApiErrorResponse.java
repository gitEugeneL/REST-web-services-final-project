package pl.university.paymentserver.applicationServerIntegration.exception;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(HttpStatus status, String message) { }
