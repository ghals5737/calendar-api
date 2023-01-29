package com.example.calendar.service.user;

import com.example.calendar.domain.user.User;
import com.example.calendar.dto.user.request.CreateUserRequest;
import com.example.calendar.dto.user.request.UpdateUserRequest;
import com.example.calendar.dto.user.response.CreateUserResponse;
import com.example.calendar.dto.user.response.SelectUserByIdResponse;
import com.example.calendar.dto.user.response.UpdateUserResponse;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@NoArgsConstructor
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

//    @AfterEach
//    public void clear() {
//        userRepository.deleteAll();
//    }


    @Test
    @DisplayName("사용자 API 조회 정상 동작 테스트")
    void selectUserByIdTest() throws Exception {
        // given
        User user = User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();
        User save = userRepository.save(user);
        log.info("getBirthday",save.getBirthday());
        log.info("getNickname",save.getNickname());
        log.info("getEmail",save.getEmail());
        log.info("password",save.getPassword());

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
    @DisplayName("사용자 생성 API 정상 동작 테스트")
    void createUserTest() {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
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
        User user = User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();

        User save = userRepository.save(user);

        // when
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(save.getId())
                .nickname("update nickname")
                .email("update email")
                .birthday(LocalDate.of(2000, 1, 24))
                .password("update password")
                .build();
        UpdateUserResponse updated = userService.updateUser(request);

        // then
        List<User> results = userRepository.findAll();
        AssertionsForInterfaceTypes.assertThat(results).hasSize(1);
        AssertionsForClassTypes.assertThat(results.get(0).getId()).isEqualTo(request.getId());
        AssertionsForClassTypes.assertThat(results.get(0).getNickname()).isEqualTo(request.getNickname());
        AssertionsForClassTypes.assertThat(results.get(0).getBirthday()).isEqualTo(request.getBirthday());
        AssertionsForClassTypes.assertThat(results.get(0).getEmail()).isEqualTo(request.getEmail());
        AssertionsForClassTypes.assertThat(results.get(0).getPassword()).isEqualTo(request.getPassword());
    }
}
