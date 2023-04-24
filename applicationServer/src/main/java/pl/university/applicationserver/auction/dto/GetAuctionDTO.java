package pl.university.applicationserver.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@Data
public class GetAuctionDTO {
    private String id;
    private String sellerEmail;
    private String sellerName;

    private String status;
    private Map<String, Decimal128> participation;

    private String name;
    private String description;

    private BigDecimal starting_price;
    private BigDecimal current_price;
    
    private LocalDateTime end_time;

}
