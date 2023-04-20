package pl.university.authenticationserver.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.university.authenticationserver.user.document.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByLogin(String login);
}
