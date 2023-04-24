package pl.university.authenticationserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class GetUserDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
