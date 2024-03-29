package com.example.calendar.service.user;

import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import com.example.calendar.dto.user.request.*;
import com.example.calendar.dto.user.response.CreateUserResponse;
import com.example.calendar.dto.user.response.LoginUserResponse;
import com.example.calendar.dto.user.response.SelectUserByEmailResponse;
import com.example.calendar.dto.user.response.SelectUserByIdResponse;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
@NoArgsConstructor
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User snsUser;

    @BeforeEach
    public void create() {
        user = userRepository.save(User.builder()
                .nickname("star")
                .password("pw")
                .email("abc@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.of(2023, 1, 26))
                .build());

        snsUser = userRepository.save(User.builder()
                .nickname("sns@gmail.com")
                .password(null)
                .email("sns@gmail.com")
                .snsType(SnsType.GOOGLE)
                .birthday(null)
                .build());
    }


    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("사용자 API 조회 정상 동작 테스트")
    void selectUserByIdTest() throws Exception {
        // given
        User user = User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();
        User save = userRepository.save(user);

        //when
        SelectUserByIdResponse response = userService.selectUserById(save.getId());

        //then
        assertThat(response.getUserId()).isEqualTo(user.getId());
        assertThat(response.getNickname()).isEqualTo(user.getNickname());
        assertThat(response.getPassword()).isEqualTo(user.getPassword());
        assertThat(response.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("사용자 이메일로 조회 정상 동작 테스트")
    void selectUserByEmailTest() throws Exception {
        // given
        User user = User.builder()
                .nickname("test nickname")
                .email("testEmail@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();
        User save = userRepository.save(user);

        //when
        List<SelectUserByEmailResponse> response = userService.selectUserByEmail(save.getEmail());

        //then
        assertThat(response.get(0).getUserId()).isEqualTo(user.getId());
        assertThat(response.get(0).getNickname()).isEqualTo(user.getNickname());
        assertThat(response.get(0).getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("사용자 생성 API 정상 동작 테스트")
    void createUserTest() throws Exception {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .nickname("nickname")
                .email("abcdefg@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();

        // when
        CreateUserResponse user = userService.createUser(request);

        // then
        assertThat(request.getNickname()).isEqualTo(user.getNickname());
        assertThat(request.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(request.getEmail()).isEqualTo(user.getEmail());
        assertThat(request.getPassword()).isEqualTo(user.getPassword());

    }

    @Test
    @DisplayName("SNS 사용자 생성 API 정상 동작 테스트")
    void createSnsUserTest() {
        // given
        CreateSnsUserRequest request = CreateSnsUserRequest.builder()
                .email("test@gmail.com")
                .birthday(null)
                .snsType(SnsType.GOOGLE)
                .build();

        // when
        CreateUserResponse result = userService.createSnsUser(request);

        // then
        assertThat(request.getEmail()).isEqualTo(result.getNickname());
        assertThat(request.getBirthday()).isEqualTo(result.getBirthday());
        assertThat(request.getSnsType()).isEqualTo(result.getSnsType());
    }

    @DisplayName("사용자 생성 API 중복으로 인한 사용자 생성 실패 테스트")
    void createUserDuplicateExceptionTest() throws Exception {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();

        // when
        userService.createUser(request);
        assertThrows(CustomException.class, () -> userService.createUser(request));

    }

    @Test
    @DisplayName("사용자 삭제 API 정상 동작 테스트")
    void deleteUserTest() {

        // given
        User user = User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();

        User save = userRepository.save(user);

        // when
        userService.deleteUserById(save.getId());

        // then
        Optional<User> byId = userRepository.findById(save.getId());
        assertThat(byId.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("사용자 수정 API 정상 동작 테스트")
    void updateUserTest() {
        // given

        // when
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(user.getId())
                .nickname("updatenickname")
                .email("updateemail@gmail.com")
                .birthday(LocalDate.of(2000, 1, 24))
                .password("updatepassword")
                .build();

        userService.updateUser(request);

        // then
        User user = userRepository.findById(this.user.getId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        assertThat(user.getId()).isEqualTo(request.getId());
        assertThat(user.getNickname()).isEqualTo(request.getNickname());
        assertThat(user.getBirthday()).isEqualTo(request.getBirthday());
        assertThat(user.getEmail()).isEqualTo(request.getEmail());
        assertThat(user.getPassword()).isEqualTo(request.getPassword());
    }

    @Test
    @DisplayName("사용자 로그인 테스트")
    void loginTest() {
        LoginUserRequest request = LoginUserRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        LoginUserResponse loginUser = userService.login(request);
        assertThat(loginUser.getUserId()).isEqualTo(user.getId());
        assertThat(loginUser.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(loginUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(loginUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("SNS 사용자 로그인 테스트")
    void loginSnsTest() {
        LoginSnsUserRequest request = LoginSnsUserRequest.builder()
                .email(snsUser.getEmail())
                .snsType(snsUser.getSnsType())
                .build();
        LoginUserResponse loginUser = userService.loginSns(request);
        assertThat(loginUser.getUserId()).isEqualTo(snsUser.getId());
        assertThat(loginUser.getBirthday()).isEqualTo(snsUser.getBirthday());
        assertThat(loginUser.getNickname()).isEqualTo(snsUser.getNickname());
        assertThat(loginUser.getEmail()).isEqualTo(snsUser.getEmail());
    }
}
