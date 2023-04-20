package pl.university.authenticationserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class GetUserDTO {
    String id;
    String firstName;
    String lastName;
}
