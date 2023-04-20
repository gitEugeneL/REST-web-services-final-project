package pl.university.authenticationserver.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.university.authenticationserver.user.dto.GetUserDTO;
import pl.university.authenticationserver.user.dto.UpdateUserDTO;
import pl.university.authenticationserver.user.exceptions.ApiRequestException;
import pl.university.authenticationserver.user.service.UserService;
import pl.university.authenticationserver.user.utils.ValidateUtils;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getUsers() {
        List<GetUserDTO> usersDTOs = userService.getUsers();
        return ResponseEntity.ok(usersDTOs);
    }


    @GetMapping("{user-id}")
    public ResponseEntity<GetUserDTO> getOneUser(@PathVariable("user-id") String id){
        GetUserDTO getUserDTO = userService.getOneUser(id); // get or throw
        return ResponseEntity.ok(getUserDTO);
    }


    @PutMapping("{user-id}")
    public ResponseEntity<String> updateUser(
            @PathVariable("user-id") String id,
            @RequestBody @Valid UpdateUserDTO dto, BindingResult bindingResult) {

        String validateErrors = ValidateUtils.validate(bindingResult); // validate dto or throw
        if (validateErrors != null) throw new ApiRequestException(validateErrors);

        userService.updateUser(dto, id); // valid dto and update user or throw
        return ResponseEntity.ok("User id: " + id + " successfully changed");
    }


    @DeleteMapping("{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") String id) {
        userService.deleteUser(id);// delete or throw
        return ResponseEntity.ok("User id: " + id + " successfully deleted");
    }
}
