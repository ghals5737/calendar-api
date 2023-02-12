package com.example.calendar.controller.user;

import com.example.calendar.dto.user.request.CreateSnsUserRequest;
import com.example.calendar.dto.user.request.CreateUserRequest;
import com.example.calendar.dto.user.request.LoginUserRequest;
import com.example.calendar.dto.user.request.UpdateUserRequest;
import com.example.calendar.dto.user.response.*;
import com.example.calendar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public SelectUserByIdResponse selectUserById(@PathVariable Long id) throws Exception {
        return userService.selectUserById(id);
    }

    @PostMapping
    public CreateUserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
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


}
