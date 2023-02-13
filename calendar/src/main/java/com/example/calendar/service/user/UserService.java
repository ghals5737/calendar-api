package com.example.calendar.service.user;

import com.example.calendar.domain.user.User;
import com.example.calendar.dto.user.request.*;
import com.example.calendar.dto.user.response.*;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.user.UserQueryDslRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.calendar.global.error.ErrorCode.*;

@RequiredArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserQueryDslRepository userQueryDslRepository;

    @Transactional
    public SelectUserByIdResponse selectUserById(Long id) {
        return UserResponse.toSelectUserByIdResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND))
        );
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request){
        if (userQueryDslRepository.existsByEmailAndSnsType(request.toUser()))
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        return UserResponse.toCreateUserResponse(
                userRepository.save(request.toUser()));
    }

    @Transactional
    public DeleteUserResponse deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        userRepository.delete(user);
        return UserResponse.toDeleteUserResponse(user);
    }

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        user.updateUser(request);
        return UserResponse.toUpdateUserResponse(user);
    }

    @Transactional
    public LoginUserResponse login(LoginUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
        if (request.getPassword().equals(user.getPassword())) {
            return UserResponse.toLoginUserResponse(user);
        }
        throw new CustomException(EMAIL_NOT_FOUND); // 로그인 실패 시?
    }

    @Transactional
    public CreateUserResponse createSnsUser(CreateSnsUserRequest request) {
        return UserResponse.toCreateUserResponse(
                userRepository.save(request.toUser()));
    }

    @Transactional
    public LoginUserResponse loginSns(LoginSnsUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (request.getSnsType().equals(user.getSnsType())) {
            return UserResponse.toLoginUserResponse(user);
        }
        throw new CustomException(SNS_TYPE_NOT_FOUND); // 로그인 실패 시?
    }
}
