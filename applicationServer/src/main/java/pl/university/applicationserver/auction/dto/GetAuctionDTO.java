package pl.university.applicationserver.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;


@AllArgsConstructor
@Data
public class GetAuctionDTO {
    private String id;
    private String sellerEmail;
    private String sellerName;

    private String status;
    private Map<String, BigDecimal> participation;

    private String name;
    private String description;

    private BigDecimal starting_price;
    private BigDecimal current_price;
    
    private Instant end_time;

    private boolean readyForPayment;
}
