package pl.university.authenticationserver.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class UpdateUserDTO {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 15, message = "must be from 2 to 20 characters")
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 15, message = "must be from 2 to 20 characters")
    private String lastName;
}

