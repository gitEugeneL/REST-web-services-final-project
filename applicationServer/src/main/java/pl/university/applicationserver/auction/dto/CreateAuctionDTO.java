package pl.university.applicationserver.auction.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateAuctionDTO {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 20, message = "must be from 5 to 20 characters")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 140, message = "must be from 5 to 140 characters")
    private String description;

    @NotNull
    @Min(1)
    @Max(9999999)
    private int startingPrice;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(one-day|three-days|one-week)$", message = "must be one-day or tree-days or one-week")
    private String lifeTime;
}
