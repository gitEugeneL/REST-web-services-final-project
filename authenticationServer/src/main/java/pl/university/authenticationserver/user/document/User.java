package pl.university.authenticationserver.user.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;


@Document
@Data
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String login;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Set<Role> roles;


    public User(String login, String passwordHash, String firstName, String lastName) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = Collections.singleton(Role.USER);
    }
}
