package pl.university.paymentserver.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.university.paymentserver.applicationServerIntegration.ApplicationIntegrationService;
import pl.university.paymentserver.applicationServerIntegration.dto.GetAuctionDTO;
import pl.university.paymentserver.authServerIntegration.dto.GetAuthUserDTO;
import com.stripe.model.checkout.Session;
import pl.university.paymentserver.payment.document.PaidAuction;
import pl.university.paymentserver.payment.exception.PaymentException;
import pl.university.paymentserver.payment.repository.PaidAuctionRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaidAuctionRepository paidAuctionRepository;
    private final ApplicationIntegrationService applicationIntegrationService;
    private GetAuctionDTO auction;
    private GetAuthUserDTO buyer;
    private String token;


    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;


    public String initialPayment(GetAuthUserDTO authUser, GetAuctionDTO auction, String token) throws StripeException {

        if (!Objects.equals(auction.getStatus(), "FINISHED") || !Objects.equals(auction.getWinnerId(), authUser.getId())) {
            throw new PaymentException("user id: " + authUser.getId() + " can't buy this product");
        }
        this.auction = auction;
        this.buyer = authUser;
        this.token = token;

        Stripe.apiKey = stripeApiKey;
        SessionCreateParams.LineItem item = SessionCreateParams.LineItem
                .builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(auction.getCurrent_price().longValue() * 100)
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(auction.getName())
                                                .setDescription(auction.getDescription())
                                                .build()
                                )
                                .build()
                )
                .setQuantity(1L)
                .build();

        return Session.create(
                SessionCreateParams.builder()
                        .addLineItem(item)
                        .setSuccessUrl("http://localhost:3000/my-purchased-auctions")
                        .setCancelUrl("http://localhost:3000/cancel")
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build()

        ).getUrl();
    }


    public void handleWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaidAuction paidAuction = new PaidAuction(
                            auction.getId(),
                            buyer.getId(),
                            auction.getName(),
                            auction.getDescription(),
                            auction.getCurrent_price(),
                            true
                    );
                    // update auction status
                    applicationIntegrationService.updateAuctionStatus(this.token, this.auction.getId());

                    paidAuctionRepository.insert(paidAuction);
                    break;
                case "payment_intent.payment_failed":
                    // payment failed
                    // --------------
                    break;
            }
        } catch (SignatureVerificationException e) {
            throw new PaymentException("Payment signature verification error");
        }
    }

    public List<PaidAuction> getPaidAuctions(GetAuthUserDTO authUser) {
        // list of purchased auctions for authorized users
        return paidAuctionRepository.findByWinnerId(authUser.getId())
                .stream()
                .collect(Collectors.toList());
    }
}