package pl.university.applicationserver.auction.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.university.applicationserver.auction.exception.ApiRequestException;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;


@Document
@Data
public class AuctionLot {
    @Id
    private String id;

    private String sellerId;
    private String seller_email;
    private String seller_name;

    private String buyer_id;
    private String buyer_email;

    private Status status;
    private Map<String, BigDecimal> participants;

    private String name;
    private String description;

    private BigDecimal starting_price;
    private BigDecimal current_price;

    private Instant start_time;
    private Instant end_time;

    private String winnerId;


    public AuctionLot(String sellerId, String seller_email, String seller_name,
                      String name, String description, BigDecimal starting_price){

        this.sellerId = sellerId;
        this.seller_email = seller_email;
        this.seller_name = seller_name;
        this.buyer_id = null;
        this.status = Status.ACTIVE;
        this.participants = null;
        this.name = name;
        this.description = description;
        this.starting_price = starting_price;
        this.current_price = null;
        this.start_time = Instant.now();
        this.winnerId = null;
    }


    public void setEndTime(String lifeTime) {
        switch (lifeTime) {
            case "test" -> this.end_time = this.start_time.plus(Duration.ofMinutes(5));
            case "one-day" -> this.end_time = this.start_time.plus(Duration.ofDays(1));
            case "three-days" -> this.end_time = this.start_time.plus(Duration.ofDays(3));
            case "one-week" -> this.end_time = this.start_time.plus(Duration.ofDays(7));
            default -> throw new ApiRequestException("Invalid lifetime value");
        }
    }

    public BigDecimal getCurrent_price() {
        if (participants != null && !participants.isEmpty()) {
            return Collections.max(participants.values());
        } else {
            return starting_price;
        }
    }
}
