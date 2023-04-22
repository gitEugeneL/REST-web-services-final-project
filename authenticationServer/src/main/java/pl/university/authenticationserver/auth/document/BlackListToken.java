package pl.university.authenticationserver.auth.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class BlackListToken {
    @Id
    private String id;
    private String token;

    public BlackListToken(String token) {
        this.token = token;
    }
}
