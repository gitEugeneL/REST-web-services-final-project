package pl.university.paymentserver.authServerIntegration.exception;

public class AuthIntegrationServerException extends RuntimeException{
    public AuthIntegrationServerException(String message) {
        super(message);
    }
}
