package pl.university.authenticationserver.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.university.authenticationserver.auth.domain.JwtRequest;
import pl.university.authenticationserver.auth.domain.JwtResponse;
import pl.university.authenticationserver.auth.service.AuthService;
import pl.university.authenticationserver.user.exceptions.ApiRequestException;
import pl.university.authenticationserver.user.utils.ValidateUtils;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid JwtRequest authRequest, BindingResult bindingResult) {
        String validateErrors = ValidateUtils.validate(bindingResult); // validate dto or throw
        if (validateErrors != null)
            throw new ApiRequestException(validateErrors);
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }


    @PostMapping("validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        return authService.validate(token)
                ? ResponseEntity.ok("token is valid")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token is not valid");
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok("User logged out successfully");
    }
}