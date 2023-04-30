package pl.university.applicationserver.auction.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.Map;


@Document
@Data
public class AuctionLot {
    @Id
    private String id;

    private String seller_id;
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

    private String winner_id;


    public AuctionLot(String seller_id, String seller_email, String seller_name,
                      String name, String description, BigDecimal starting_price){

        this.seller_id = seller_id;
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
        this.winner_id = null;
    }

    public void setEnd_time(String lifetime) {
        switch (lifetime) {
            case "test" -> this.end_time = this.start_time.plus(Duration.ofMinutes(5)); // only for test
            case "one-day" -> this.end_time = this.start_time.plus(Duration.ofDays(1));
            case "tree-days" -> this.end_time = this.start_time.plus(Duration.ofDays(3));
            case "one-week" -> this.end_time = this.start_time.plus(Duration.ofDays(7));
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
