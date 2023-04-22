package pl.university.authenticationserver.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.university.authenticationserver.user.document.User;
import pl.university.authenticationserver.user.repository.UserRepository;
import pl.university.authenticationserver.user.utils.PasswordEncoder;


@Configuration
public class UserSeeder {
    @Bean
    CommandLineRunner runner(UserRepository userRepository) {
        return args -> {
            User user = new User(
                    "test@test.com",
                    PasswordEncoder.encode("0000"),
                    "firstname-1",
                    "lastname-1"
            );

            userRepository.findByLogin(user.getLogin())
                .ifPresentOrElse(
                    u -> System.out.println("test user already exist")
                    , () -> userRepository.insert(user)
                );
        };
    }
}
