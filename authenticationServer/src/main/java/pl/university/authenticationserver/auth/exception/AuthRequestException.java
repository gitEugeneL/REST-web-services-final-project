package pl.university.authenticationserver.auth.exception;

public class AuthRequestException extends RuntimeException{
    public AuthRequestException(String message) {
        super(message);
    }
}
