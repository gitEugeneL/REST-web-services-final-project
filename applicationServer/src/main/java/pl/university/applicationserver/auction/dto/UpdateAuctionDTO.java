package pl.university.applicationserver.auction.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAuctionDTO {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 140, message = "must be from 5 to 140 characters")
    private String description;

    public UpdateAuctionDTO() {}
}
