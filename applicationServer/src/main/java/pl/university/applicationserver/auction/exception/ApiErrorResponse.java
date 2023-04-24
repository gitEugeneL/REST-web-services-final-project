package pl.university.applicationserver.auction.exception;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(HttpStatus status, String message) { }
