package pl.university.applicationserver.authServerIntegration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class GetAuthUserDTO {
    String id;
    String email;
    String firstName;
    String lastName;

    public GetAuthUserDTO() {}
}
