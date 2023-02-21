package com.example.calendar.controller.user;

import com.example.calendar.dto.user.request.*;
import com.example.calendar.dto.user.response.*;
import com.example.calendar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public SelectUserByIdResponse selectUserById(@PathVariable Long id) throws Exception {
        return userService.selectUserById(id);
    }

    @PostMapping
    public CreateUserResponse createUser(@RequestBody @Valid CreateUserRequest request) throws Exception {
        return userService.createUser(request);
    }

    @PostMapping("/sns")
    public CreateUserResponse createSnsUser(@RequestBody @Valid CreateSnsUserRequest request) {
        return userService.createSnsUser(request);
    }

    @DeleteMapping("/{id}")
    public DeleteUserResponse deleteUser(@PathVariable Long id) throws Exception {
        return userService.deleteUserById(id);
    }

    @PutMapping
    public UpdateUserResponse updateUser(@RequestBody @Valid UpdateUserRequest request) throws Exception {
        return userService.updateUser(request);
    }

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody @Valid LoginUserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/sns-login")
    public LoginUserResponse login(@RequestBody @Valid LoginSnsUserRequest request) {
        return userService.loginSns(request);
    }


}
