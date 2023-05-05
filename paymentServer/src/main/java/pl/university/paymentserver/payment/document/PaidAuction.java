package pl.university.paymentserver.payment.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Document
@Data
public class PaidAuction {
    @Id
    private String id;
    private String auctionId;
    @Indexed
    private String winnerId;

    private String name;
    private String description;

    private BigDecimal finalPrice;
    private boolean isPaid;

    public PaidAuction(String auctionId, String winnerId, String name,
                       String description, BigDecimal finalPrice, boolean isPaid) {
        this.auctionId = auctionId;
        this.winnerId = winnerId;
        this.name = name;
        this.description = description;
        this.finalPrice = finalPrice;
        this.isPaid = isPaid;
    }
}
