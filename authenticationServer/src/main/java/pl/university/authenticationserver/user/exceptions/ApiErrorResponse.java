package pl.university.authenticationserver.user.exceptions;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(HttpStatus status, String message) { }
