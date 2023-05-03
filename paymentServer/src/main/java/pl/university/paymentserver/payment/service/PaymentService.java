package pl.university.paymentserver.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.university.paymentserver.applicationServerIntegration.dto.GetAuctionDTO;
import pl.university.paymentserver.authServerIntegration.dto.GetAuthUserDTO;
import com.stripe.model.checkout.Session;

import java.util.Objects;


@Service
public class PaymentService {

    private String auctionId;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;


    public String initialPayment(GetAuthUserDTO authUser, GetAuctionDTO auction) throws StripeException {

        if (!Objects.equals(auction.getStatus(), "FINISHED") || !Objects.equals(auction.getWinnerId(), authUser.getId())) {
            // todo exception
        }
        auctionId = auction.getId();

        Stripe.apiKey = stripeApiKey;
        SessionCreateParams.LineItem item = SessionCreateParams.LineItem
                .builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setCurrency("pln")
                                .setUnitAmount(auction.getCurrent_price().longValue())
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
                        .setSuccessUrl("http://localhost:8083/success") // todo add client port and page
                        .setCancelUrl("http://localhost:8083/cancel") // // todo add client port and page
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build()

        ).getUrl();
    }


    public void handleWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    // todo "payment was successful"
                    System.out.println(auctionId); // todo PUT request to application server (edit auction status) and !!!ADD authUser token
                    break;
                case "payment_intent.payment_failed":
                    // todo "payment failed"
                    break;
            }
        } catch (SignatureVerificationException e) {
            // todo exception
        }
    }
}