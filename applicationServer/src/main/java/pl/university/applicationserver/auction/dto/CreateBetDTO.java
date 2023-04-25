package pl.university.applicationserver.auction.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateBetDTO {

    @NotNull
    @NotEmpty
    private String auctionId;

    @NotNull
    @Min(1)
    @Max(9999999)
    private int bet;
}
