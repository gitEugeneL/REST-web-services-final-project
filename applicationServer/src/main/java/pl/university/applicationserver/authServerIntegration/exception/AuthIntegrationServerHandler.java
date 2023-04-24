package pl.university.applicationserver.authServerIntegration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthIntegrationServerHandler {
    @ExceptionHandler(value = {AuthIntegrationServerException.class})
    protected ResponseEntity<ApiErrorResponse> handleApiRequestException(AuthIntegrationServerException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
