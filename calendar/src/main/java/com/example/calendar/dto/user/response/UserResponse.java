package com.example.calendar.dto.user.response;

import com.example.calendar.domain.user.User;

public class UserResponse {
    public static SelectUserByIdResponse toSelectUserByIdResponse(User user) {
        return SelectUserByIdResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .password(user.getPassword())
                .snsType(user.getSnsType())
                .build();
    }

    public static CreateUserResponse toCreateUserResponse(User user) {
        return CreateUserResponse.builder()
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .password(user.getPassword())
                .userId(user.getId())
                .snsType(user.getSnsType())
                .build();
    }

    public static DeleteUserResponse toDeleteUserResponse(User user) {
        return DeleteUserResponse.builder()
                .userId(user.getId())
                .build();
    }

    public static UpdateUserResponse toUpdateUserResponse(User user) {
        return UpdateUserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .password(user.getPassword())
                .snsType(user.getSnsType())
                .build();
    }

    public static LoginUserResponse toLoginUserResponse(User user) {
        return LoginUserResponse.builder()
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userId(user.getId())
                .snsType(user.getSnsType())
                .build();
    }
}
