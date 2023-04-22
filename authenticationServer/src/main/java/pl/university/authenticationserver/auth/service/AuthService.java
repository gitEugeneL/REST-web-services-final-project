package pl.university.authenticationserver.auth.service;

import lombok.NonNull;
import pl.university.authenticationserver.auth.document.BlackListToken;
import pl.university.authenticationserver.auth.domain.JwtRequest;
import pl.university.authenticationserver.auth.domain.JwtResponse;
import pl.university.authenticationserver.auth.provider.JwtProvider;
import pl.university.authenticationserver.auth.repository.AuthRepository;
import pl.university.authenticationserver.auth.utils.PasswordDecoder;
import pl.university.authenticationserver.user.document.User;
import pl.university.authenticationserver.user.service.UserService;
import pl.university.authenticationserver.auth.exception.AuthRequestException;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthRepository authRepository;


    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthRequestException("email or password is not valid"));

        if (PasswordDecoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken);

        } else {
            throw new AuthRequestException("email or password is not valid");
        }
    }


    public void logout(@NonNull String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);

            if (authRepository.findByToken(token).isEmpty())
                authRepository.insert(new BlackListToken(token));
        } else
            throw new AuthRequestException("token is not valid");
    }


    public boolean validate(@NonNull String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtProvider.validateAccessToken(token);
        }
        return false;
    }
}

