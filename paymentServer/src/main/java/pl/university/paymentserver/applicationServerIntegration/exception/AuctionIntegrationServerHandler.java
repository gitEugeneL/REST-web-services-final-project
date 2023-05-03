package pl.university.paymentserver.applicationServerIntegration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuctionIntegrationServerHandler {
    @ExceptionHandler(value = {AuctionIntegrationServerException.class})
    protected ResponseEntity<ApiErrorResponse> handleApiRequestException(AuctionIntegrationServerException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
