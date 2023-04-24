package pl.university.applicationserver.auction.document;

import lombok.Data;
import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private Map<String, Decimal128> participation;

    private String name;
    private String description;

    private BigDecimal starting_price;
    private BigDecimal current_price;

    private LocalDateTime start_time;
    private LocalDateTime end_time;


    public AuctionLot(String seller_id, String seller_email, String seller_name,
                      String name, String description, BigDecimal starting_price){

        this.seller_id = seller_id;
        this.seller_email = seller_email;
        this.seller_name = seller_name;
        this.buyer_id = null;
        this.status = Status.ACTIVE;
        this.participation = null;
        this.name = name;
        this.description = description;
        this.starting_price = starting_price;
        this.current_price = null;
        this.start_time = LocalDateTime.now();
    }

    public void setEnd_time(String lifetime) {
        switch (lifetime) {
            case "one-day" -> this.end_time = this.start_time.plusDays(1);
            case "tree-days" -> this.end_time = this.start_time.plusDays(3);
            case "one-week" -> this.end_time = this.start_time.plusWeeks(1);
        }
    }
}
