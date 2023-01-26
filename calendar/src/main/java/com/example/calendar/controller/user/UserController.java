package com.example.calendar.controller.user;

import com.example.calendar.dto.user.request.CreateUserRequest;
import com.example.calendar.dto.user.request.UpdateUserRequest;
import com.example.calendar.dto.user.response.CreateUserResponse;
import com.example.calendar.dto.user.response.DeleteUserResponse;
import com.example.calendar.dto.user.response.SelectUserByIdResponse;
import com.example.calendar.dto.user.response.UpdateUserResponse;
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

    @DeleteMapping("/{id}")
    public DeleteUserResponse deleteUser(@PathVariable Long id) throws Exception {
        return userService.deleteUserById(id);
    }

    @PutMapping
    public UpdateUserResponse updateUser(@RequestBody @Valid UpdateUserRequest request) throws Exception {
        return userService.updateUser(request);
    }
}
