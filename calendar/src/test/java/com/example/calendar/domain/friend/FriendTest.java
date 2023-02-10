package com.example.calendar.domain.friend;

import com.example.calendar.domain.user.User;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@NoArgsConstructor
class FriendTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    private User mainUser;
    private User subUser;

    @BeforeEach
    void beforeEach() {
        mainUser = userRepository.save(User.builder()
                .nickname("mainUser")
                .password("pw")
                .email("mainUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        subUser = userRepository.save(User.builder()
                .nickname("subUser")
                .password("pw")
                .email("subUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
        userRepository.save(mainUser);
        userRepository.save(subUser);
    }

    @AfterEach
    void afterEach() {
        friendRepository.deleteAll();
    }

    @Test
    void createFriendTest() {

        FriendId friendId = FriendId.builder()
                .mainUserId(mainUser.getId())
                .subUserId(subUser.getId())
                .build();

        friendRepository.save(Friend.builder()
                .id(friendId)
                .build());

    }

}