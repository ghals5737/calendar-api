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

    private User sendUser;
    private User receiveUser;

    @BeforeEach
    void beforeEach() {
        sendUser = userRepository.save(User.builder()
                .nickname("sendUser")
                .password("pw")
                .email("sendUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        receiveUser = userRepository.save(User.builder()
                .nickname("receiveUser")
                .password("pw")
                .email("receiveUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
        userRepository.save(sendUser);
        userRepository.save(receiveUser);
    }

    @AfterEach
    void afterEach() {
        friendRepository.deleteAll();
    }

    @Test
    void createFriendTest() {

        FriendId friendId = FriendId.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        friendRepository.save(Friend.builder()
                .id(friendId)
                .build());

    }

}