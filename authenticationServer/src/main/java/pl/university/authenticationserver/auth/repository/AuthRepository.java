package pl.university.authenticationserver.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.university.authenticationserver.auth.document.BlackListToken;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<BlackListToken, String> {
    Optional<BlackListToken> findByToken(String token);
}
