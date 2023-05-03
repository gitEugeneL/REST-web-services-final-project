package pl.university.paymentserver.payment.controller;

import com.stripe.exception.StripeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.university.paymentserver.applicationServerIntegration.ApplicationIntegrationService;
import pl.university.paymentserver.applicationServerIntegration.dto.GetAuctionDTO;
import pl.university.paymentserver.authServerIntegration.AuthIntegrationService;
import pl.university.paymentserver.authServerIntegration.dto.GetAuthUserDTO;
import pl.university.paymentserver.payment.service.PaymentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/payment")
public class PaymentController {

    private final AuthIntegrationService authIntegrationService;
    private final ApplicationIntegrationService applicationIntegrationService;
    private final PaymentService paymentService;


    @GetMapping("/{auctionId}")
    public ResponseEntity<String> initialPayment(@NonNull @RequestHeader("Authorization") String token,
                                                 @PathVariable String auctionId) throws StripeException {
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get finished auction in which the authUser won or throw
        GetAuctionDTO auction = applicationIntegrationService.getAuction(token, auctionId);
        // get payment url or throw
        String paymentUrl = paymentService.initialPayment(authUser, auction);

        return ResponseEntity.ok(paymentUrl);
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        // check payment
        paymentService.handleWebhook(payload, sigHeader);

        return ResponseEntity.ok().build();
    }
}



