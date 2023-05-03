package pl.university.paymentserver.applicationServerIntegration.exception;

public class AuctionIntegrationServerException extends RuntimeException{
    public AuctionIntegrationServerException(String message) {
        super(message);
    }
}
