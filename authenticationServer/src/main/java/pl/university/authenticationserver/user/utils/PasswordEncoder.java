package pl.university.authenticationserver.user.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {

    public static String encode(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
