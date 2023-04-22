package pl.university.authenticationserver.auth.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class PasswordDecoder {
    public static boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
