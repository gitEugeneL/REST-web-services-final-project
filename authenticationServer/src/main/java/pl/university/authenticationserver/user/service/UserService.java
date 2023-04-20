package pl.university.authenticationserver.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.university.authenticationserver.user.document.User;
import pl.university.authenticationserver.user.dto.GetUserDTO;
import pl.university.authenticationserver.user.dto.UpdateUserDTO;
import pl.university.authenticationserver.user.exceptions.ApiRequestException;
import pl.university.authenticationserver.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<GetUserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new GetUserDTO(user.getId(), user.getLastName(), user.getFirstName()))
                .collect(Collectors.toList());
    }


    public GetUserDTO getOneUser(String id) {
         return userRepository.findById(id)
                .map(user -> new GetUserDTO(user.getId(), user.getFirstName(), user.getLastName()))
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));
    }


    public void updateUser(UpdateUserDTO dto, String id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));
    }


    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("User not found for id: " + id));
        userRepository.delete(user);
    }
}
