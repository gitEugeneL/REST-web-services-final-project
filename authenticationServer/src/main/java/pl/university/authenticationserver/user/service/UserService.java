package pl.university.authenticationserver.user.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.university.authenticationserver.user.document.User;
import pl.university.authenticationserver.user.dto.CreateUserDTO;
import pl.university.authenticationserver.user.dto.GetUserDTO;
import pl.university.authenticationserver.user.dto.UpdateUserDTO;
import pl.university.authenticationserver.user.exceptions.ApiRequestException;
import pl.university.authenticationserver.user.repository.UserRepository;
import pl.university.authenticationserver.user.utils.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void createUser(CreateUserDTO dto) {

        String login = dto.getLogin();
        if (this.getByLogin(login).isPresent()) {
           throw new ApiRequestException("User already exists");
        }

        User newUser = new User(
                login,
                PasswordEncoder.encode(dto.getPassword()),
                dto.getFirstName(),
                dto.getLastName()
        );

        userRepository.insert(newUser);
    }


    public List<GetUserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new GetUserDTO(user.getId(), user.getLogin(), user.getLastName(), user.getFirstName()))
                .collect(Collectors.toList());
    }


    public GetUserDTO getOneUser(String id) {
         return userRepository.findById(id)
                .map(user -> new GetUserDTO(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName()))
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));
    }


    public GetUserDTO getAuthUser() {
        // only auth user data
        String authUserLogin = this.getAuthUserLogin();
        //--------------------
        return userRepository.findByLogin(authUserLogin)
                .map(user -> new GetUserDTO(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName()))
                .orElseThrow(() -> new ApiRequestException("User does not exist"));
    }


    public void updateUser(UpdateUserDTO dto, String id) {
        //-----------------------------
        // user can only update himself.
        //-----------------------------
        userRepository.findById(id)
                .map(user -> {
                    if (!Objects.equals(user.getLogin(), this.getAuthUserLogin())) {
                        throw new ApiRequestException("User is not authorized to update this profile");
                    }
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));
    }


    public void deleteUser(String id) {
        //-----------------------------
        // user can only delete himself.
        //-----------------------------
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));

       if (!Objects.equals(user.getLogin(), this.getAuthUserLogin()))
           throw new ApiRequestException("User is not authorized to delete this profile");

       userRepository.delete(user);
    }


    public Optional<User> getByLogin(@NonNull String login) {
        return userRepository.findByLogin(login).stream().filter(user -> login.equals(user.getLogin())).findFirst();
    }


    private String getAuthUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}